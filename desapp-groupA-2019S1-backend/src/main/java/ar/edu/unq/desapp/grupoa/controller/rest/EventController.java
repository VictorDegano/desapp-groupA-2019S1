package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.DTOConverter;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.EventHomeDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Transactional
@Controller
public class EventController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventService eventService;

    public EventController() {
        LOGGER.info("Starting Fiesta Controller");
    }

    @GetMapping(value= "/event/{eventId}")
    public ResponseEntity<Event> findEvent(@PathVariable Integer eventId){
        LOGGER.info("Got request GET for a Event with id {}", eventId);
        Event event = eventService.getById(eventId);
        LOGGER.info("Responding with Event with id {}", eventId);
        return new ResponseEntity<>( event, HttpStatus.OK) ;
    }

    @GetMapping(value="/event/in_progress/{userId}")
    public ResponseEntity<ArrayList<EventHomeDTO>> eventsInProgress(@PathVariable Integer userId){
        LOGGER.info("Got request GET for Event In Progress for user {}", userId);

        List<Event> eventsInProgress = eventService.getEventsInProgressForUser(userId);
        ArrayList<EventHomeDTO> eventsDTOList = DTOConverter.createEventHomeDTOList(eventsInProgress);

    LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>( eventsDTOList, HttpStatus.OK) ;
    }

    // TODO: 1/6/2019 Falta hacer los test
    @GetMapping(value="/event/last_events/{userId}")
    public ResponseEntity<List<EventHomeDTO>> lastEvents(@PathVariable Integer userId){
        LOGGER.info("Got request GET for Last Event of user {}", userId);

        List<Event> lastEvents = eventService.getLastEventsForUser(userId);
        List<EventHomeDTO> eventsDTOList = DTOConverter.createEventHomeDTOList(lastEvents);

        LOGGER.info("Responding with Event Lists {}", eventsDTOList);
        return new ResponseEntity<>( eventsDTOList, HttpStatus.OK) ;
    }

    //PlaceHolder so Heroku Runs
    @GetMapping(value= "/")
    public ResponseEntity<String> root(){
        return new ResponseEntity<>( "Hello World", HttpStatus.OK) ;
    }
}
