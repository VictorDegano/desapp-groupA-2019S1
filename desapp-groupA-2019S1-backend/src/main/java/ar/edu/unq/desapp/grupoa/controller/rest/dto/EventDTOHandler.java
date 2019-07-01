package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;

public abstract class EventDTOHandler {

    public abstract boolean canHandle(Event event);

    public abstract EventDTO from(Event event);
}
