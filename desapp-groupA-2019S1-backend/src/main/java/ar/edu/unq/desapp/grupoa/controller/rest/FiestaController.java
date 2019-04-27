package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.service.FiestaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FiestaController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FiestaService fiestaService;

    public FiestaController() {
        LOGGER.info("Starting Fiesta Controller");
    }

    @GetMapping(value= "/fiesta/{fiestaId}")
    public ResponseEntity<Fiesta> findFiesta(@PathVariable Integer fiestaId){
        LOGGER.info("Got request GET for fiesta with id {}", fiestaId);
        Fiesta fiesta = fiestaService.getById(fiestaId);
        LOGGER.info("Responding with fiesta with id {}", fiestaId);
        return new ResponseEntity<>( fiesta, HttpStatus.OK) ;
    }

    //PlaceHolder so Heroku Runs
    @GetMapping(value= "/")
    public ResponseEntity<String> root(){
        return new ResponseEntity<>( "Hello World", HttpStatus.OK) ;
    }
}
