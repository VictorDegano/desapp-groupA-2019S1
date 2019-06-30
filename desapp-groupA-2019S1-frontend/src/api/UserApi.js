import axios from "axios";
import { auth } from "../components/Root.js";
import { API_CONFIG } from "./Configs/Api-config.js";

class UserApi {
  getUser(userId) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.get(API_CONFIG.endPoint + "user/" + userId, header);
  }

  fetchUser(userId) {
    // console.log('fetchUser()');
    return this.getUser(userId)
      .then(response => {
        return response.data;
      })
      .catch(error => []);
    //TODO: habria que pensar un mejor handleo.
  }

  postUser(user, uri) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.post(API_CONFIG.endPoint + uri, user, header);
  }

  loginUser(user) {
    // console.log('loginUser()');
    return this.postUser(user, "google/login/")
      .then(response => {
        return response.data;
      })
      .catch(error => []);
    //TODO: habria que pensar un mejor handleo.
  }

  // retrieveLoggedUser(user) {
  //   // console.log('loginUser()');
  //   return this.postUser(user, "google/login/")
  //     .then(response => {
  //       return response.data;
  //     })
  //     .catch(error => []);
  //   //TODO: habria que pensar un mejor handleo.
  // }

  logoutUser(userToLogout) {
    // console.log('logoutUser()');
    return this.postUser(userToLogout, "google/logout/")
      .then(response => {
        return response.data;
      })
      .catch(error => []);
    //TODO: habria que pensar un mejor handleo.
  }

  putUser(user) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios
      .put(API_CONFIG.endPoint + "user", user, header)
      .then(response => {
        return response.data;
      })
      .catch(error => []);
  }
}

export default UserApi;
