import axios from "axios";
import { auth } from "../components/Root";
import { API_CONFIG } from "./Configs/Api-config";

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
}

export default UserApi;
