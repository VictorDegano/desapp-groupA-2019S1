package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.apache.commons.collections.ListUtils.EMPTY_LIST;

@Service
public class BootstrapService {

    @Autowired
    private FiestaService fiestaService;

    BootstrapService(){
    }

    public void createExampleData() {
        this.fiestaService.createAll(Arrays.asList(pepeLocoFiesta(),ivanFiesta()));
    }

    private Fiesta ivanFiesta() {
        return new Fiesta(
                        "La fiesta de Ivan",
                        createUserWithName("Ivan"),
                        Arrays.asList(
                                    new Guest(createUserWithName("Victor")),
                                    new Guest(createUserWithName("Pepe El Loco")),
                                    new Guest(createUserWithName("Ivan"))),
                        LocalDateTime.now(),
                        EMPTY_LIST);
    }

    private Fiesta pepeLocoFiesta() {
        return new Fiesta(
                "Pepallooza",
                createUserWithName("Pepe Loco"),
                Arrays.asList(
                        new Guest(createUserWithName("Ivan")),
                        new Guest(createUserWithName("Ivan"))),
                LocalDateTime.now(),
                EMPTY_LIST);
    }

    private User createUserWithName(String name) {
        return new User(name,"Doe","nameless@acaradeperro.com","123", LocalDateTime.now());
    }

}
