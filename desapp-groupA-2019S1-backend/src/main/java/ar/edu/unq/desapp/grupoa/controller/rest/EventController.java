package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.EventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.GenericEventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.FiestaDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/event", method = {RequestMethod.GET, RequestMethod.POST})
public class EventController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventService eventService;

    public EventController() {
        LOGGER.info("Starting Fiesta Controller");
    }

    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDTO> findEvent(@PathVariable Integer eventId) {
        LOGGER.info("Got request GET for a Event with id {}", eventId);
        Event event = eventService.getById(eventId);

        EventDTO eventDTO = EventDTO.from(event);

        LOGGER.info("Responding with Event with id {}", eventId);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/in_progress/{userId}")
    public ResponseEntity<List<GenericEventDTO>> eventsInProgress(@PathVariable Integer userId) {
        LOGGER.info("Got request GET for Event In Progress for user {}", userId);

        List<Event> eventsInProgress = eventService.getEventsInProgressForUser(userId);
        List<GenericEventDTO> eventsDTOList = GenericEventDTO.fromList(eventsInProgress);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/last_events/{userId}")
    public ResponseEntity<List<GenericEventDTO>> lastEvents(@PathVariable Integer userId) {
        LOGGER.info("Got request GET for Last Event of user {}", userId);

        List<Event> lastEvents = eventService.getLastEventsForUser(userId);
        List<GenericEventDTO> eventsDTOList = GenericEventDTO.fromList(lastEvents);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
        LOGGER.info("Got request POST to create a Fiesta Event with data {}", eventDTO);

        Integer eventId = eventDTO.handleCreation(this.eventService);

        LOGGER.info("Responding with Fiesta Event with id {}", eventId);
        LOGGER.info("Event Fiesta created {}", eventService.getById(eventId));
        return new ResponseEntity<>(eventId.toString(), HttpStatus.CREATED);
    }
//

//    // TODO: 27/6/2019 Falta hacer los test
//    @GetMapping("/event/most_popular_events/")
//    public ResponseEntity<List<EventHomeDTO>> mostPopularEvents(){
//        LOGGER.info("Got request GET for Last Event of user");
//
//        List<Event> mostPopularEvents = eventService.mostPopularEvents();
//        List<EventHomeDTO> eventsDTOList = DTOConverter.createEventHomeDTOList(mostPopularEvents);
//
//        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
//        return new ResponseEntity<>(eventsDTOList, HttpStatus.OK);
//    }

}
