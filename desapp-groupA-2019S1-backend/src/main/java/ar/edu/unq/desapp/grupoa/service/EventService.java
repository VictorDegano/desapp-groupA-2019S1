package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.EventNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.GuestNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.event.GoodTypeException;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.GoodDAO;
import ar.edu.unq.desapp.grupoa.persistence.GuestDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.LoadGood.loadGood;

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
    private GoodDAO goodDAO;
    @Autowired
    private EmailSenderService emailSenderService;

    public EventService(EventDAO aEventDAO) {
        this.eventDAO = aEventDAO;
    }

    public EventService() {
    }

    public Integer createFiesta(String name, Integer organizerId, List<String> guestsMails,
                                LocalDateTime limitConfirmationDateTime, List<Good> goods) {



        Fiesta fiesta = new Fiesta(
                name,
                getUser(organizerId),
                getGuests(guestsMails),
                limitConfirmationDateTime,
                goods, LocalDateTime.now());

        sendInvitationToGuests(fiesta);
        return create(fiesta);
    }

    public Integer createCanasta(String name, Integer organizerId, List<String> guestMails, List<Good> goods) {

        Canasta canasta = new Canasta(
                name,
                getUser(organizerId),
                getGuests(guestMails),
                goods,
                LocalDateTime.now());

        sendInvitationToGuests(canasta);
        return create(canasta);
    }


    public Integer createBaquitaComunitary(String eventName, Integer organizerId, List<String> guestMail, List<Good> goods) {
        BaquitaComunitary baquitaComunitary = new BaquitaComunitary(
                eventName,
                getUser(organizerId),
                getGuests(guestMail),
                goods,
                LocalDateTime.now()
        );

        sendInvitationToGuests(baquitaComunitary);
        return create(baquitaComunitary);
    }

    public Integer createBaquitaRepresentatives(String eventName, Integer organizerId, List<String> guestMail, List<Good> goods) {
        BaquitaRepresentatives baquitaRepresentatives = new BaquitaRepresentatives(
                eventName,
                getUser(organizerId),
                getGuests(guestMail),
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


    private List<Guest> getGuests(List<String> emailList) {
         return emailList.stream().map(email -> new Guest(userDAO.findByEmail(email))).collect(Collectors.toList());
   }


    private User getUser(Integer userId) {
        return userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
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


    public void inviteUserToEvent(Integer eventId, Integer userId) {
        Event event = getById(eventId);
        User user =  getUser(userId);
        sendMailToUser(event.getName(),event.getOrganizer().getEmail(),user);
        event.inviteUser(user);
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
            sendMailToUser(event.getName(), organizerEmail, guestUser);
        });
    }

    private void sendMailToUser(String eventName, String organizerEmail, User userToInvite) {
        emailSenderService.sendInvitation(organizerEmail,userToInvite.getEmail(),userToInvite.getFirstName(),eventName);
    }

    public void ownCanastaGood(Integer eventId, Integer userId, Integer goodId) {
        Canasta canasta = (Canasta) eventDAO.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        CanastaGood good = (CanastaGood) goodDAO.findById(goodId).orElseThrow(()-> new GoodTypeException("Good not found"));
        User user =  getUser(userId);

        canasta.ownAGood(user,good);
        eventDAO.save(canasta);
    }

    public void ownBaquitaGood(Integer eventId, Integer userId, Integer goodId) {
        BaquitaRepresentatives baquita = (BaquitaRepresentatives) eventDAO.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        Good good =  goodDAO.findById(goodId).orElseThrow(()-> new GoodTypeException("Good not found"));
        User representative =  getUser(userId);
        loadGood(baquita,good,representative);
        eventDAO.save(baquita);
    }
}
