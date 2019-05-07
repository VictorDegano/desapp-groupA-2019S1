package ar.edu.unq.desapp.grupoa.runner;

import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.service.FiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.collections.ListUtils.EMPTY_LIST;

@Component
public class BootStrapRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FiestaService fiestaService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Initializing BootStrapRunner");
        this.createExampleData();
    }

    private void createExampleData() {
        logger.info("Loading Sample Data");
        this.fiestaService.createAll(Arrays.asList(pepeLocoFiesta(),ivanFiesta()));
    }

    private Fiesta ivanFiesta() {
        logger.info("Creating Ivan Fiesta");
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
        logger.info("Creating Pepe Loco Fiesta");
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
