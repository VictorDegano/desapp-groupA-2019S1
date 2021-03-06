package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.CanastaDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class CanastaDTOHandler implements EventDTOHandler{
    @Override
    public boolean canHandle(Event event) {
        return event.getType().equals(EventType.CANASTA);
    }

    @Override
    public EventDTO from(Event event) {
        CanastaDTO canastaDTO = new CanastaDTO();
        return canastaDTO.from(event);
    }
}
