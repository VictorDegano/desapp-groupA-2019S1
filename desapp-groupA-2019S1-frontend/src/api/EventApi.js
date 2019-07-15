import axios from "axios";
import { auth } from "../components/Root";
import { API_CONFIG } from "./Configs/Api-config";

class EventApi {
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

  getEvent(eventNumber) {
    return axios.get(API_CONFIG.endPoint + "event/" + eventNumber, this.header);
  }

  getEventosEnCurso(userId) {
    return axios.get(
      API_CONFIG.endPoint + "event/in_progress/" + userId,
      this.header
    );
  }

  getMisUltimosEventos(userId) {
    return axios.get(
      API_CONFIG.endPoint + "event/last_events/" + userId,
      this.header
    );
  }

  getEventosMasPopulares() {
    return axios.get(
      API_CONFIG.endPoint + "event/most_popular_events/",
      this.header
    );
  }

  fetchEvents() {
    return this.getEventosEnCurso(1)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        return error;
      });
  }

  createEvent(event) {
    return axios.post(API_CONFIG.endPoint + "event/", event, this.header);
  }

  ownGood(eventId, goodId, loggedUserId) {
    return axios
      .put(
        API_CONFIG.endPoint +
          `event/ownCanastaGood/${eventId}/${loggedUserId}/${goodId}`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return error;
      });
  }

  closeEvent(eventId) {
    return axios
      .put(API_CONFIG.endPoint + `event/closeEvent/${eventId}/`, this.header)
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return false;
      });
  }

  aceptInvitation(eventId, guestId) {
    return axios
      .put(
        API_CONFIG.endPoint + `event/confirmAsistance/${eventId}/${guestId}`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return false;
      });
  }

  getTotalCost(eventId) {
    return axios.get(
      API_CONFIG.endPoint + `event/eventCost/${eventId}`,
      this.header
    );
  }

  takeGood(eventId, goodId, userId) {
    return axios
      .put(
        API_CONFIG.endPoint +
          `event/ownBaquitaGood/${eventId}/${userId}/${goodId}`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return error;
      });
  }

  resendInvitation(eventId, userID) {
    return axios
      .put(
        API_CONFIG.endPoint + `event/inviteUser/${eventId}/${userID}`,
        this.header
      )
      .then(response => {
        return response.status === 200 ? true : false;
      })
      .catch(error => {
        return false;
      });
  }
}

export default EventApi;
