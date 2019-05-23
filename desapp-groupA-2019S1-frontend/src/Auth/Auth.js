import auth0 from 'auth0-js';
import {AUTH_CONFIG} from './Auth0-config';
import history from '../history';

export default class Auth {
    userProfile;
    accessToken;
    idToken;
    expiresAt;
    auth0 = new auth0.WebAuth({
        domain: AUTH_CONFIG.domain,
        clientID: AUTH_CONFIG.clientID,
        redirectUri: AUTH_CONFIG.redirectUri,
        audience: 'https://eventeando.auth.com/api',
        responseType: 'token id_token',
        scope: 'openid profile'
    });

    login() {
        this.auth0.authorize();
    }

    logout() {
        // Remove tokens and expiry time
        this.accessToken = null;
        this.idToken = null;
        this.expiresAt = 0;
        // Remove the user
        this.userProfile = null;
        // Remove isLoggedIn flag from localStorage
        localStorage.removeItem('isLoggedIn');

        this.auth0.logout({
            returnTo: window.location.origin
        });

        // navigate to the home route
        history.replace('/home');
    }

    setSession(authResult) {
        console.log(authResult);
        // Set isLoggedIn flag in localStorage
        localStorage.setItem('isLoggedIn', 'true');

        // Set the time that the Access Token will expire at
        let expiresAt = (authResult.expiresIn * 1000) + new Date().getTime();
        this.accessToken = authResult.accessToken;
        this.idToken = authResult.idToken;
        this.expiresAt = expiresAt;
    }

    renewSession() {
    this.auth0.checkSession({}, (err, authResult) => {
        if (authResult && authResult.accessToken && authResult.idToken) {
            this.setSession(authResult);
        } else if (err) {
            this.logout();
            console.log(err);
            alert(`Could not get a new token (${err.error}: ${err.error_description}).`);
        }
    });
    }

    isAuthenticated() {
        // Check whether the current time is past the
        // access token's expiry time
        let expiresAt = this.expiresAt;
        return new Date().getTime() < expiresAt;
    }

    handleAuthentication() {
        console.log('handleAuthentication()');
        this.auth0.parseHash((err, authResult) => {
            if(authResult && authResult.accessToken && authResult.idToken){
                this.setSession(authResult);
            } else if (err) {
                //TODO: Hay que contemplar que hacer en el caso que no se autentique, 
                //se podria informar con un modal y retornarlo a la pagina de inicion
                console.log(err)
            }
        });
    }

    getAccessToken() {
        return this.accessToken;
    }
    
    getIdToken() {
        return this.idToken;
    }

    getProfile(cb) {
        this.auth0.client.userInfo(this.accessToken, (err, profile) => {
          if (profile) {
            this.userProfile = profile;
          }
          cb(err, profile);
        });
    }

    //Esto es para evitar perder la referencia
    constructor() {
        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
        this.handleAuthentication = this.handleAuthentication.bind(this);
        this.isAuthenticated = this.isAuthenticated.bind(this);
        this.setSession = this.setSession.bind(this);
        this.getAccessToken = this.getAccessToken.bind(this);
        this.getIdToken = this.getIdToken.bind(this);
        this.renewSession = this.renewSession.bind(this);
        this.getProfile = this.getProfile.bind(this);
    }
}
