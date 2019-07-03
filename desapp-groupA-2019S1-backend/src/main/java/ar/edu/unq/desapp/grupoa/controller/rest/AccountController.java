package ar.edu.unq.desapp.grupoa.controller.rest;


import ar.edu.unq.desapp.grupoa.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/event", method = {RequestMethod.GET, RequestMethod.POST})
public class AccountController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    public AccountController() {
        LOGGER.info("Starting Account Controller");
    }


//    @PostMapping()
//    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
//        LOGGER.info("Got request POST to create a Event with data {}", eventDTO);
//
//        Integer eventId = eventDTO.handleCreation(this.eventService);
//
//        LOGGER.info("Responding with Fiesta Event with id {}", eventId);
//        LOGGER.info("Event Fiesta created {}", eventService.getById(eventId));
//        return new ResponseEntity<>(eventId.toString(), HttpStatus.CREATED);
//    }
//
//    @PutMapping(value = "/confirmAsistance/{eventId}/{guestId}")
//    public ResponseEntity<String> confirmAsistance(@PathVariable Integer eventId,@PathVariable Integer guestId) {
//        LOGGER.info("Got request PUT to confirm assistance Fiesta Event with data");
//
//        eventService.confirmAsistance(eventId, guestId);
//
//        LOGGER.info("Assistance of guest {} confirmed for event {}",eventId,guestId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @GetMapping(value = "/{eventId}")
//    public ResponseEntity<EventDTO> findEvent(@PathVariable Integer eventId) {
//        LOGGER.info("Got request GET for a Event with id {}", eventId);
//        Event event = eventService.getById(eventId);
//
//        EventDTO eventDTO = EventDTO.fromEvent(event);
//
//        LOGGER.info("Responding with Event with id {}", eventId);
//        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
//    }
//


}
