package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.FiestaDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class FiestaDTOHandler implements EventDTOHandler {
    @Override
    public boolean canHandle(Event event) {
        return event.getType().equals(EventType.FIESTA);
    }

    @Override
    public EventDTO from(Event event) {
        FiestaDTO fiestaDTO = new FiestaDTO();
        return fiestaDTO.from(event);
    }
}
