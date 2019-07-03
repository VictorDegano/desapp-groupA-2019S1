package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/event", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class EventController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventService eventService;

    public EventController() {
        LOGGER.info("Starting Event Controller");
    }

    @PostMapping()
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
        LOGGER.info("Got request POST to create a Event with data {}", eventDTO);

        Integer eventId = eventDTO.handleCreation(this.eventService);

        LOGGER.info("Responding with Fiesta Event with id {}", eventId);
        LOGGER.info("Event Fiesta created {}", eventService.getById(eventId));
        return new ResponseEntity<>(eventId.toString(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/confirmAsistance/{eventId}/{guestId}")
    public ResponseEntity<String> confirmAsistance(@PathVariable Integer eventId,@PathVariable Integer guestId) {
        LOGGER.info("Got request PUT to confirm assistance Fiesta Event with data");

        eventService.confirmAsistance(eventId, guestId);

        LOGGER.info("Assistance of guest {} confirmed for event {}",eventId,guestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/inviteUser/{eventId}/{userID}")
    public ResponseEntity<String> inviteToEvent(@PathVariable Integer eventId,@PathVariable Integer userId) {
        LOGGER.info("Got request PUT to confirm assistance Fiesta Event with data");

        eventService.inviteUserToEvent(eventId, userId);

        LOGGER.info("Assistance of guest {} confirmed for event {}",eventId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/closeEvent/{eventId}/")
    public ResponseEntity<String> closeEvent(@PathVariable Integer eventId) {
        LOGGER.info("Got request PUT to close event");

        eventService.closeEvent(eventId);

        LOGGER.info("Event with id {} closed",eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDTO> findEvent(@PathVariable Integer eventId) {
        LOGGER.info("Got request GET for a Event with id {}", eventId);
        Event event = eventService.getById(eventId);

        EventDTO eventDTO = EventDTO.fromEvent(event);

        LOGGER.info("Responding with Event with id {}", eventId);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/eventCost/{eventId}")
    public ResponseEntity<Integer> getEventCost(@PathVariable Integer eventId) {
        LOGGER.info("Got request GET for Event cost with id {}", eventId);
        Integer cost = eventService.getEventCost(eventId);


        LOGGER.info("Responding with cost for event with id {}", eventId);
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }


    @GetMapping(value = "/in_progress/{userId}")
    public ResponseEntity<List<EventDTO>> eventsInProgress(@PathVariable Integer userId) {
        LOGGER.info("Got request GET for Event In Progress for user {}", userId);

        List<Event> eventsInProgress = eventService.getEventsInProgressForUser(userId);
        List<EventDTO> eventsDTOList = EventDTO.fromList(eventsInProgress);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/last_events/{userId}")
    public ResponseEntity<List<EventDTO>> lastEvents(@PathVariable Integer userId) {
        LOGGER.info("Got request GET for Last Event of user {}", userId);

        List<Event> lastEvents = eventService.getLastEventsForUser(userId);
        List<EventDTO> eventsDTOList = EventDTO.fromList(lastEvents);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
    }

    @GetMapping("/most_popular_events/")
    public ResponseEntity<List<EventDTO>> mostPopularEvents(){
        LOGGER.info("Got request GET for Last Event of user");

        List<Event> mostPopularEvents = eventService.mostPopularEvents();
        List<EventDTO> eventsDTOList = EventDTO.fromList(mostPopularEvents);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
    }

}
