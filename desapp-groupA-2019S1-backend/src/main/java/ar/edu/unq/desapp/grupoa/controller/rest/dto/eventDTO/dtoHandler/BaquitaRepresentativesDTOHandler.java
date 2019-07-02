package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.BaquitaRepresentativesDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class BaquitaRepresentativesDTOHandler implements EventDTOHandler {
    @Override
    public boolean canHandle(Event event) {
        return event.getType().equals(EventType.BAQUITA_REPRESENTATIVES);
    }

    @Override
    public EventDTO from(Event event) {
        BaquitaRepresentativesDTO baquitaRepresentativesDTO = new BaquitaRepresentativesDTO();
        return baquitaRepresentativesDTO.from(event);
    }
}
