import fiestaMock from "../resources/Fiesta.js";
import axios from "axios";
import {auth} from "../components/Root.js";
import {API_CONFIG} from "./Configs/Api-config.js";

class EventApi {
    
    getEvent(eventNumber){
        const accessToken= auth.getAccessToken();

        const header = {
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Content-Type": API_CONFIG.contentType,
                "Access-Control-Allow-Methods": API_CONFIG.allowMethods,
                "Access-Control-Allow-Origin": API_CONFIG.allowOrigin
            }
        };

        return axios.get( API_CONFIG.endPoint + "fiesta/" + eventNumber, header);
    }

    fetchEvents(){
        // console.log('fetchEvents()');
        return this.getEvent("1")
                   .then( (response) => {
                            let events = this.getAllEvents();
                            events.push(response.data);
                            return events;
                            })
                    .catch((error) => []);
                    //TODO: habria que pensar un mejor handleo.
    }

    getAllEvents(){
        // console.log('getAllEvents()');
        return fiestaMock.Fiestas;
    }
}

export default EventApi;