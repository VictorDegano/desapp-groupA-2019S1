import React from "react";
import EventTable from "./EventTable";


class EventsComponent extends React.Component{
    render() {
        return<div>
            <h1>{this.props.title}</h1>
            <EventTable arrayDeEventos={this.props.arrayDeEventos}/>
        </div>
    }

}

export default EventsComponent;