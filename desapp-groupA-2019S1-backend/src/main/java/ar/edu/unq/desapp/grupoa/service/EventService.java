package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.EventNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserDAO userDAO;

    public EventService(EventDAO aEventDAO) {
        this.eventDAO = aEventDAO;
    }

    public EventService() {
    }

    public Integer createFiesta(String name, Integer organizerId, List<Integer> guestsId,
                                LocalDateTime limitConfirmationDateTime, List<Good> goods) {

        Fiesta fiesta = new Fiesta(
                name,
                getOrganizer(organizerId),
                getGuests(guestsId),
                limitConfirmationDateTime,
                goods, LocalDateTime.now());

        return create(fiesta);
    }

    public Integer createCanasta(String name, Integer organizerId, List<Integer> guestsId, List<Good> goods) {

        Canasta canasta = new Canasta(
                name,
                getOrganizer(organizerId),
                getGuests(guestsId),
                goods,
                LocalDateTime.now());

        return create(canasta);
    }

    public Integer createBaquitaComunitary(String eventName, Integer organizerId, List<Integer> guestsId, List<Good> goods) {
        BaquitaComunitary baquitaComunitary = new BaquitaComunitary(
                eventName,
                getOrganizer(organizerId),
                getGuests(guestsId),
                goods,
                LocalDateTime.now()
        );

        return create(baquitaComunitary);
    }

    public List<Integer> createAll(List<Event> aListOfEvents) {
        this.eventDAO.saveAll(aListOfEvents);
        List<Integer> idList = aListOfEvents.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        return idList;
    }

    public Event getById(Integer eventID) {
        return this.eventDAO.findById(eventID).orElseThrow(() -> new EventNotFoundException(eventID));
    }

    public List<Event> getEventsInProgressForUser(Integer userId) {
        return eventDAO.getEventsInProgressForUser(userId);
    }

    public List<Event> getLastEventsForUser(Integer userId) {
        return eventDAO.getLastEventsForUser(userId);
    }

    public List<Event> mostPopularEvents() {
        return eventDAO.getMostPopularEventsForUser();
    }


    private List<Guest> getGuests(List<Integer> guestsId) {
        return userDAO.findAllById(guestsId)
                .stream().map(Guest::new).collect(Collectors.toList());
    }

    private User getOrganizer(Integer organizerId) {
        return userDAO.findById(organizerId).orElseThrow(() -> new UserNotFoundException(organizerId));
    }


    public Integer create(Event event) {
        eventDAO.save(event);
        return event.getId();
    }


}
