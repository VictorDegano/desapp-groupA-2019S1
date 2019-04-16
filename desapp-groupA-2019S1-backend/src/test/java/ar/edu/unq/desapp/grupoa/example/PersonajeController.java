package ar.edu.unq.desapp.grupoa.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonajeController {

    //Si bien para este ejemplo no estoy usandolo,
    // es buena practica loggear lo que pasa a nivel entrada/salida de tu aplicacion
    private final Logger      LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PersonajeService personajeService;


    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }


    @PostMapping(value = "/personaje")
    public ResponseEntity<String> createPersonaje(@RequestBody Personaje personaje) {
        Integer personajeId = personajeService.create(personaje);
        return new ResponseEntity<>(personajeId.toString(), HttpStatus.CREATED);
    }



    @GetMapping(value= "/personaje/{personajeId}")
    public ResponseEntity<Personaje> findPersonaje(@PathVariable Integer personajeId){
        Personaje personaje = personajeService.getById(personajeId);
        return new ResponseEntity<>( personaje, HttpStatus.OK) ;
    }

    @PutMapping( value = "/personaje")
    public ResponseEntity<?> updatePersonaje(@RequestBody Personaje personaje){
        this.personajeService.update(personaje);
        return new ResponseEntity<>(null,HttpStatus.OK );
    }

    @PutMapping( value = "/personaje/{personajeId}")
    public ResponseEntity<?> pickUpItem(@RequestBody Item item, @PathVariable Integer personajeId){
        this.personajeService.pickUpItem(personajeId,item);
        return new ResponseEntity<>(null,HttpStatus.OK );
    }

}


