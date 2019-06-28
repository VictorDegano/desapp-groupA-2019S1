import auth0 from "auth0-js";
import { AUTH_CONFIG } from "./Auth0-config";
import history from "../history";
import UserApi from "../api/UserApi.js";
//Actions Type
import * as User_Type from "../actions/Action_Types/User_Types.js";
//Store
import { store } from "../index.js";

export default class Auth {
  userProfile;

  auth0 = new auth0.WebAuth({
    domain: AUTH_CONFIG.domain,
    clientID: AUTH_CONFIG.clientID,
    redirectUri: AUTH_CONFIG.redirectUri,
    audience: "https://eventeando.auth.com/api",
    responseType: "token id_token",
    scope:
      "profile email openid https://www.googleapis.com/auth/user.birthday.read"
  });

  login() {
    // console.log('login()');
    this.auth0.authorize();
  }

  logout() {
    // console.log("logout()");
    const userToLogout = {
      userId: store.getState().UserReducer.loggedUser.id,
      accessToken: localStorage.getItem("id_token")
    };

    const userApi = new UserApi();
    userApi
      .logoutUser(userToLogout)
      .then(response => {
        store.dispatch({ type: User_Type.LOGOUT_USER, payload: null });

        // Remove the user
        this.userProfile = null;

        // Remove isLoggedIn flag from localStorage
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("access_token");
        localStorage.removeItem("id_token");
        localStorage.removeItem("expires_at");
        localStorage.removeItem("email");
        localStorage.removeItem("first_name");
        localStorage.removeItem("last_name");
        localStorage.removeItem("picture");

        this.auth0.logout({
          returnTo: window.location.origin
        });
      })
      .catch(error => {
        alert("An error has occurred, please try again");
      });

    // navigate to the home route
    // history.replace("/home");
  }

  setSession(authResult) {
    // console.log('setSession()');
    // Set isLoggedIn flag in localStorage
    // localStorage.setItem("isLoggedIn", "true");

    // Set the time that the Access Token will expire at
    let expiresAt = authResult.expiresIn * 1000 + new Date().getTime();

    localStorage.setItem("access_token", authResult.accessToken);
    localStorage.setItem("id_token", authResult.idToken);
    localStorage.setItem("expires_at", expiresAt);
    localStorage.setItem("email", authResult.idTokenPayload.email);
    localStorage.setItem("first_name", authResult.idTokenPayload.given_name);
    localStorage.setItem("last_name", authResult.idTokenPayload.family_name);
    localStorage.setItem("picture", authResult.idTokenPayload.picture);
  }

  validSession(authResult) {
    return authResult && authResult.accessToken && authResult.idToken;
  }

  resolveRenewSession(error, authResult) {
    if (this.validSession(authResult)) {
      this.setSession(authResult);
    } else if (error) {
      this.logout();
      // console.log(error); //TODO: valdria la pena que vaya al home?
      alert(
        `Could not get a new token (${error.error}: ${error.error_description}).`
      );
    }
  }

  retrieveLoggedUser() {
    console.log("retrieveLoggedUser()");
    const userApi = new UserApi();

    const userToPost = {
      accessToken: localStorage.getItem("id_token"),
      expireAt: localStorage.getItem("expires_at"),
      email: localStorage.getItem("email"),
      firstName: localStorage.getItem("first_name"),
      familyName: localStorage.getItem("last_name")
    };

    userApi.loginUser(userToPost).then(user => {
      console.log(user);
      store.dispatch({ type: User_Type.LOGIN_USER, payload: user });
    });
  }

  renewSession() {
    // console.log('renewSession()');
    this.auth0.checkSession({}, (err, authResult) =>
      this.resolveRenewSession(err, authResult)
    );
  }

  isAuthenticated() {
    // console.log('isAuthenticated()');
    // Check whether the current time is past the
    // access token's expiry time
    let expiresAt = localStorage.getItem("expires_at");
    return new Date().getTime() < expiresAt;
  }

  resolveHandleAuthentication(error, authResult) {
    if (this.validSession(authResult)) {
      this.setSession(authResult);
      this.retrieveLoggedUser();
    } else if (error) {
      //TODO: Hay que contemplar que hacer en el caso que no se autentique,
      //se podria informar con un modal y retornarlo a la pagina de inicion
      alert(
        `Could not be authenticated (${error.error}: ${error.error_description}).`
      );
    }
  }

  handleAuthentication() {
    // console.log('handleAuthentication()');
    this.auth0.parseHash((err, authResult) =>
      this.resolveHandleAuthentication(err, authResult)
    );
  }

  getAccessToken() {
    // console.log('getAccessToken()');
    return localStorage.getItem("access_token");
  }

  getIdToken() {
    // console.log('getIdToken()');
    return localStorage.getItem("id_token");
  }

  resolveGetProfile(callback, error, profile) {
    if (profile) {
      this.userProfile = profile;
    }
    callback(error, profile);
  }

  getProfile(cb) {
    // console.log('getProfile()');
    this.auth0.client.userInfo(
      localStorage.getItem("access_token"),
      (err, profile) => this.resolveGetProfile(cb, err, profile)
    );
  }

  //Esto es para evitar perder la referencia
  constructor() {
    this.resolveHandleAuthentication = this.resolveHandleAuthentication.bind(
      this
    );
    this.validSession = this.validSession.bind(this);
    this.resolveRenewSession = this.resolveRenewSession.bind(this);
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.handleAuthentication = this.handleAuthentication.bind(this);
    this.isAuthenticated = this.isAuthenticated.bind(this);
    this.setSession = this.setSession.bind(this);
    this.getAccessToken = this.getAccessToken.bind(this);
    this.getIdToken = this.getIdToken.bind(this);
    this.renewSession = this.renewSession.bind(this);
    this.getProfile = this.getProfile.bind(this);
    this.retrieveLoggedUser = this.retrieveLoggedUser.bind(this);
  }
}
