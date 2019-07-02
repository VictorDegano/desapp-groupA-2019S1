import axios from "axios";
import { auth } from "../components/Root";
import { API_CONFIG } from "./Configs/Api-config";

class EventApi {
  getEvent(eventNumber) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.get(API_CONFIG.endPoint + "event/" + eventNumber, header);
  }

  getEventosEnCurso(userId) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.get(
      API_CONFIG.endPoint + "event/in_progress/" + userId,
      header
    );
  }

  getMisUltimosEventos(userId) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.get(
      API_CONFIG.endPoint + "event/last_events/" + userId,
      header
    );
  }

  getEventosMasPopulares() {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.get(
      API_CONFIG.endPoint + "event/most_popular_events/",
      header
    );
  }

  fetchEvents() {
    // console.log('fetchEvents()');
    return this.getEventosEnCurso(1)
      .then(response => {
        // let events = this.getAllEvents();
        // events.push(response);
        return response.data;
      })
      .catch(error => []);
    //TODO: habria que pensar un mejor handleo.
  }

  createEvent(event) {
    const accessToken = auth.getAccessToken();

    const header = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": API_CONFIG.contentType,
        "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
        "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
      }
    };

    return axios.post(
      API_CONFIG.endPoint + "event/create_fiesta/",
      event,
      header
    );
  }
}

export default EventApi;
