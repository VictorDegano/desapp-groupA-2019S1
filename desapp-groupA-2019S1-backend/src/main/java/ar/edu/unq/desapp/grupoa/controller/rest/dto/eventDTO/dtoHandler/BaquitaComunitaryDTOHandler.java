package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.BaquitaComunitariaDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class BaquitaComunitaryDTOHandler implements EventDTOHandler {
    @Override
    public boolean canHandle(Event event) {
        return event.getType().equals(EventType.BAQUITA_COMUNITARY);
    }

    @Override
    public EventDTO from(Event event) {
        BaquitaComunitariaDTO baquitaDTO = new BaquitaComunitariaDTO();
        return baquitaDTO.from(event);
    }
}
