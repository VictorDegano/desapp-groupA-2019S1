package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class CreateBaquitaComunitariaStrategy implements CreateEventStrategy{
    @Override
    public boolean hasToHandleCreateEvent(EventType aEventType) {
        return aEventType.equals(EventType.BAQUITA_COMUNITARY);
    }

    @Override
    public Event createEvent(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, LocalDateTime creationDate) {
        return BaquitaComunitary.createWithATemplate(name, organizer, guests, template, creationDate);
    }
}
