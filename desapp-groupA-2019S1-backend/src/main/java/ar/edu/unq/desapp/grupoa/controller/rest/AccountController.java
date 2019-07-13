package ar.edu.unq.desapp.grupoa.controller.rest;


import ar.edu.unq.desapp.grupoa.controller.rest.dto.CreditDTO;
import ar.edu.unq.desapp.grupoa.model.account.Credit;
import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.service.AccountService;

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
        LOGGER.info("Got request PUT to deposit {} to user {}", amount, userId);

        accountService.depositMoney(userId,amount);

        LOGGER.info("{} deposited to {}", amount, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/extractMoney/{userId}/{amount}")
    public ResponseEntity<String> extractMoney(@PathVariable Integer userId,@PathVariable Integer amount ) {
        LOGGER.info("Got request PUT to extract {} to user {}", amount, userId);

        accountService.extractMoney(userId,amount);

        LOGGER.info("{} extracted fromFiestaGood {}", amount, userId);
        return new ResponseEntity<>(amount.toString(),HttpStatus.OK);
    }

    @PutMapping("/takeLoan/{userId}")
    public ResponseEntity<String> takeLoan(@PathVariable Integer userId ) {
        LOGGER.info("Got request PUT to loan fromFiestaGood {}", userId);

        accountService.loan(userId);

        LOGGER.info("{} took a Loan", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/creditsOnCourse/{userId}")
    public ResponseEntity<CreditDTO> creditOnCourse(@PathVariable Integer userId ) {
        LOGGER.info("Got request GET to get credits on course fromFiestaGood {}", userId);

        Credit credit = accountService.getCreditsOnCourse(userId);
        CreditDTO creditDTO = CreditDTO.from(credit);

        LOGGER.info("returning credits on course for {}", userId);
        return new ResponseEntity<CreditDTO>(creditDTO,HttpStatus.OK);
    }

    @GetMapping("/movements/{userId}")
    public ResponseEntity<List<Movement>> movements(@PathVariable Integer userId ) {
        LOGGER.info("Got request GET to get user movements {}", userId);

        List<Movement> movements = accountService.getMovements(userId);

        LOGGER.info("returning movements fromFiestaGood {}", userId);
        return new ResponseEntity<List<Movement>>(movements,HttpStatus.OK);
    }

}
