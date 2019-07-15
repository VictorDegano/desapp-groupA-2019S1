import axios from "axios";
import { auth } from "../components/Root";
import { API_CONFIG } from "./Configs/Api-config";

class AccountApi {
  constructor() {
    this.accessToken = auth.getAccessToken();

    this.header = {
      headers: {
        Authorization: `Bearer ${this.accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };
  }

  depositMoney(amount, userId) {
    return axios
      .put(
        API_CONFIG.endPoint + `account/depositMoney/${userId}/${amount}/`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return error;
      });
  }
}

export default AccountApi;