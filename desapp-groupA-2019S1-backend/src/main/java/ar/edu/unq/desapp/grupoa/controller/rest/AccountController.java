package ar.edu.unq.desapp.grupoa.controller.rest;


import ar.edu.unq.desapp.grupoa.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/account", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class AccountController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    public AccountController() {
        LOGGER.info("Starting Account Controller");
    }


    @PutMapping("/depositMoney/{userId}/{amount}")
    public ResponseEntity<String> depositMoney(@PathVariable Integer userId,@PathVariable Integer amount ) {
        LOGGER.info("Got request POST to deposit {} to user {}", amount, userId);

        accountService.depositMoney(userId,amount);

        LOGGER.info("{} deposited to {}", amount, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/extractMoney/{userId}/{amount}")
    public ResponseEntity<String> extractMoney(@PathVariable Integer userId,@PathVariable Integer amount ) {
        LOGGER.info("Got request POST to extract {} to user {}", amount, userId);

        accountService.extractMoney(userId,amount);

        LOGGER.info("{} extracted from {}", amount, userId);
        return new ResponseEntity<>(amount.toString(),HttpStatus.OK);
    }

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
