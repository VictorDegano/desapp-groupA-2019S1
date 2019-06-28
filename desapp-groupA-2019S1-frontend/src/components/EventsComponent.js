import React from "react";
import EventTable from "./EventTable";

function EventsComponent(props) {
  return (
    <div>
      <h1 className="text-white">{props.title}</h1>
      <EventTable arrayDeEventos={props.arrayDeEventos} />
    </div>
  );
}

export default EventsComponent;
