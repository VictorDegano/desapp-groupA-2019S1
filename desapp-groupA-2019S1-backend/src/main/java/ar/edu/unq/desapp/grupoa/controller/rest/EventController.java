package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GenericEventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.FiestaDTO;
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
    public ResponseEntity<Event> findEvent(@PathVariable Integer eventId) {
        LOGGER.info("Got request GET for a Event with id {}", eventId);
        Event event = eventService.getById(eventId);
        LOGGER.info("Responding with Event with id {}", eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
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

    @PostMapping("/create_fiesta")
    public ResponseEntity<String> createFiesta(@Valid @RequestBody FiestaDTO fiestaDTO) {
        LOGGER.info("Got request POST to create a Fiesta Event with data {}", fiestaDTO);

        Integer eventId = eventService.createFiesta(fiestaDTO);
        LOGGER.info("Responding with Fiesta Event with id {}", eventId);
        LOGGER.info("Event Fiesta created {}", eventService.getById(eventId));
        return new ResponseEntity<>(eventId.toString(), HttpStatus.CREATED);
    }


}
