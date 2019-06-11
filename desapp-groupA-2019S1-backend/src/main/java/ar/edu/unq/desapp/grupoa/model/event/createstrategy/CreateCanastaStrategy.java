package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import java.time.LocalDateTime;
import java.util.List;

public class CreateCanastaStrategy implements CreateEventStrategy {

    @Override
    public boolean hasToHandleCreateEvent(EventType aEventType) {
        return aEventType == EventType.CANASTA;
    }

    @Override
    public Event createEvent(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, LocalDateTime creationDate) {
        return Canasta.createWithATemplate(name, organizer, guests, template, creationDate);
    }
}