import fiestaMock from '../resources/Fiesta.js';
import axios from 'axios';

class EventApi {

    constructor() {
        if(window.location.href === 'http://localhost:3000/'
           && window.location.host === "localhost:3000"
           && window.location.hostname === "localhost"
           && window.location.origin === 'http://localhost:3000'
           && window.location.port === '3000'){
            this.endPoint = 'http://localhost:8080/';
        } else{
            this.endPoint = 'https://desapp-grupoa-2019s1-backend.herokuapp.com/';
        }
    }

    getEvent(eventNumber){
        var header = {
            headers: {
                'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
                'Access-Control-Allow-Origin': '*'
            }
        }


        return axios.get( this.endPoint + 'fiesta/' + eventNumber, header);
    }

    fetchEvents(){
        console.log('fetchEvents()');
        return this.getEvent('1')
                   .then( response =>{
                            var events = this.getAllEvents();
                            events.push(response.data);
                            return events;
                            });
    }

    getAllEvents(){
        console.log('getAllEvents()');
        return fiestaMock.Fiestas;
    }

}

export default EventApi