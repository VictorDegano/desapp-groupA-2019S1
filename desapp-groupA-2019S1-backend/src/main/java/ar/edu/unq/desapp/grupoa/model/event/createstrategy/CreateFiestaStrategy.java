package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import java.time.LocalDateTime;
import java.util.List;

public class CreateFiestaStrategy implements CreateEventStrategy {

    @Override
    public boolean hasToHandleCreateEvent(EventType aEventType) {
        return aEventType == EventType.FIESTA;
    }

    @Override
    public Event createEvent(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, LocalDateTime creationDate) {
        return Fiesta.createWithATemplate(name, organizer, guests, limitTime, template, creationDate);
    }
}
