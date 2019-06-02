import React from "react";
import EventList from "../containers/EventList";
import {withRouter} from "react-router-dom";
import NavigationBar from "./NavigationBar";
import EventsComponent from "./EventsComponent";


function getEventos(){
    return [
        {
            "id":1,
            "name":"La fiesta de Ivan",
            "organizer":{
                "id":1,
                "firstName":"Ivan",
                "lastName":"Dominikow",
                "email":"ivanDominikow@acaradeperro.com"
            },
            "type":"FIESTA",
            "quantityOfGuest":0
        },
        {
            "id":2,
            "name":"IvanFest",
            "organizer":{
                "id":1,
                "firstName":"Ivan",
                "lastName":"Dominikow",
                "email":"ivanDominikow@acaradeperro.com"
            },
            "type":"FIESTA",
            "quantityOfGuest":0
        },
        {
            "id":3,
            "name":"Canasteando 2.0",
            "organizer":{
                "id":1,
                "firstName":"Ivan",
                "lastName":"Dominikow",
                "email":"ivanDominikow@acaradeperro.com"
            },
            "type":"CANASTA",
            "quantityOfGuest":0
        }
    ]

};
const App = () => (
  <div>
    <NavigationBar/>
    <EventsComponent title={"Mis eventos en curso:"} arrayDeEventos={getEventos()}/>
    <EventsComponent title={"Mis ultimos eventos:"} arrayDeEventos={getEventos()}/>
    <EventsComponent title={"Los eventos mas populares:" } arrayDeEventos={getEventos()}/>
    <EventList/>
  </div>
);

export default withRouter(App);
