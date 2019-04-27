package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import static ar.edu.unq.desapp.grupoa.model.utils.RandomUserGenerator.randomUserWithName;
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
                        randomUserWithName("Ivan"),
                        Arrays.asList(
                                    new Guest(randomUserWithName("Victor")),
                                    new Guest(randomUserWithName("Pepe El Loco")),
                                    new Guest(randomUserWithName("Ivan"))),
                        LocalDateTime.now(),
                        EMPTY_LIST);
    }

    private Fiesta pepeLocoFiesta() {
        return new Fiesta(
                "Pepallooza",
                randomUserWithName("Pepe Loco"),
                Arrays.asList(
                        new Guest(randomUserWithName("Ivan")),
                        new Guest(randomUserWithName("Ivan"))),
                LocalDateTime.now(),
                EMPTY_LIST);
    }


}
