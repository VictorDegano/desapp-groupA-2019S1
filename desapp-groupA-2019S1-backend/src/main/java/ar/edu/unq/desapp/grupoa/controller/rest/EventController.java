package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin
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
    public ResponseEntity<List<Event>> eventsInProgress(@PathVariable Integer userId){
        LOGGER.info("Got request GET for Event In Progress for user {}", userId);
        List<Event> eventsInProgress = eventService.getEventsInProgressForUser(userId);
        LOGGER.info("Responding with Event Lists {}", userId);
        return new ResponseEntity<>( eventsInProgress, HttpStatus.OK) ;
    }

    //PlaceHolder so Heroku Runs
    @GetMapping(value= "/")
    public ResponseEntity<String> root(){
        return new ResponseEntity<>( "Hello World", HttpStatus.OK) ;
    }
}
