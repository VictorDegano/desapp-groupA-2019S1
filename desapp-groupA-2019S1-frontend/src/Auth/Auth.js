import auth0 from 'auth0-js';
import {AUTH_CONFIG} from './Auth0-Variables';

export default class Auth {
    auth0 = new auth0.WebAuth({
        domain: AUTH_CONFIG.domain,
        clientID: AUTH_CONFIG.clientID,
        redirectUri: AUTH_CONFIG.redirectUri,
        responseType: 'token id_token',
        scope: 'openid profile'
    });

    login() {
        this.auth0.authorize();
    }

    handleAuthentication() {
        this.auth0.parseHash((err, authResult) => {
            
            if(authResult){
                localStorage.setItem('access_token', authResult.accessToken)
                localStorage.setItem('id_token', authResult.idToken)

                let expireAt = JSON.stringify(authResult.expiresIn * 1000 + new Date().getTime());
                localStorage.setItem('expiresAt', expireAt)
            } else {
                console.log(err)
            }
        });
    }
    
    logout() {
        // Clear access token and ID token from local storage
        localStorage.removeItem('access_token');
        localStorage.removeItem('id_token');
        localStorage.removeItem('expiresAt');
    }

}