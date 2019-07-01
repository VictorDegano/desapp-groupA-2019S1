package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;

public interface  EventDTOHandler {

    public abstract boolean canHandle(Event event);

    public abstract EventDTO from(Event event);
}
