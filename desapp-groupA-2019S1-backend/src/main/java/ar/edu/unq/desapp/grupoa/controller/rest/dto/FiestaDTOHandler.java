package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;

public class FiestaDTOHandler extends EventDTOHandler {
    @Override
    public boolean canHandle(Event event) {
        return event.getType().equals(EventType.FIESTA);
    }

    @Override
    public EventDTO from(Event event) {
        return FiestaDTO.from((Fiesta) event);
    }
}
