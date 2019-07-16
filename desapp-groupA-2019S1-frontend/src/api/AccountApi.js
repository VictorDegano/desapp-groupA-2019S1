import axios from "axios";
import { toast } from "react-toastify";
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

  getUserBalance(userId) {
    return axios
      .get(API_CONFIG.endPoint + `account/userBalance/${userId}/`, this.header)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        return error;
      });
  }

  extractMoney(amount, userId) {
    return axios
      .put(
        API_CONFIG.endPoint + `account/extractMoney/${userId}/${amount}/`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return error;
      });
  }

  getUserMovements(userId) {
    return axios
      .get(API_CONFIG.endPoint + `account/movements/${userId}/`, this.header)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        return error;
      });
  }

  takeALoan(userId) {
    return axios
      .put(API_CONFIG.endPoint + `account/takeLoan/${userId}/`, this.header)
      .then(response => {
        toast("Loan Succesfully Taken", { type: "success" });
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        toast(error.response.data, { type: "error" });
        return error;
      });
  }

  creditsOnCourse(userId) {
    return axios
      .put(
        API_CONFIG.endPoint + `account/creditsOnCourse/${userId}/`,
        this.header
      )
      .then(response => {
        return response.data;
      })
      .catch(error => {
        toast(error.response.data, { type: "error" });
        return error;
      });
  }
}

export default AccountApi;
