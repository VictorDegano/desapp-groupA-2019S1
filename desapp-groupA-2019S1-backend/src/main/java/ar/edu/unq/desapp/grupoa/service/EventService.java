package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.EventNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.GuestNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.GuestDAO;
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
    @Autowired
    private GuestDAO guestDAO;
    @Autowired
    private EmailSenderService emailSenderService;

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

        sendInvitationToGuests(fiesta);
        return create(fiesta);
    }

    public Integer createCanasta(String name, Integer organizerId, List<Integer> guestsId, List<Good> goods) {

        Canasta canasta = new Canasta(
                name,
                getOrganizer(organizerId),
                getGuests(guestsId),
                goods,
                LocalDateTime.now());

        sendInvitationToGuests(canasta);
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

        sendInvitationToGuests(baquitaComunitary);
        return create(baquitaComunitary);
    }

    public Integer createBaquitaRepresentatives(String eventName, Integer organizerId, List<Integer> guestsId, List<Good> goods) {
        BaquitaRepresentatives baquitaRepresentatives = new BaquitaRepresentatives(
                eventName,
                getOrganizer(organizerId),
                getGuests(guestsId),
                goods,
                LocalDateTime.now()
        );

        sendInvitationToGuests(baquitaRepresentatives);
        return create(baquitaRepresentatives);
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


    public void confirmAsistance(Integer eventId, Integer guestId) {
        Event event = getById(eventId);
        Guest guest = guestDAO.findById(guestId).orElseThrow(() -> new GuestNotFoundException(guestId));
        event.confirmAsistancesOf(guest);

        eventDAO.save(event);
    }

    public Integer getEventCost(Integer eventId) {
        Event event = getById(eventId);
        return event.totalCost();
    }

    public void closeEvent(Integer eventId) {
        Event event = getById(eventId);
        event.close();
        eventDAO.save(event);
    }


    private void sendInvitationToGuests(Event event) {
        String organizerEmail = event.getOrganizer().getEmail();
        event.getGuest().stream().forEach(guest -> {
            User guestUser = guest.getUser();
            emailSenderService.sendInvitation(organizerEmail,guestUser.getEmail(),guestUser.getFirstName(),event.getName());
        });
    }
}
