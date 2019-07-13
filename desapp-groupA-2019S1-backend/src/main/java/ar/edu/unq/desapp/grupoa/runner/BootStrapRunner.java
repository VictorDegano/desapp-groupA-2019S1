package ar.edu.unq.desapp.grupoa.runner;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.Baquita;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.collections.ListUtils.EMPTY_LIST;

@Component
public class BootStrapRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventService eventService;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Initializing BootStrapRunner");
        this.createExampleData();
    }

    private void createExampleData() throws JsonProcessingException {
        logger.info("Loading Sample Data");
        User ivanDominikow  = createUserWithName("Ivan",
                                        "Dominikow" ,
                                           "ivanDominikow@acaradeperro.com",
                                        "iv123",
                                                  LocalDateTime.of(1992,10,12,22,10));
        User ivanTamargo    = createUserWithName("Ivan",
                                        "Tamargo" ,
                                           "ivanjaratamargo@gmail.com",
                                        "it333",
                                                  LocalDateTime.of(1992,5,11,6,12));
        User victorDegano   = createUserWithName("Victor",
                                        "Degano" ,
                                           "victordegano@gmail.com",
                                        "vd3511",
                                                  LocalDateTime.of(1990,01,29,17,10));
        User pepeLocura   = createUserWithName("Pepe",
                                      "Locura" ,
                                         "pepelocura@gmail.com",
                                      "pepelocura",
                                                LocalDateTime.of(1987,10,19,20,10));

        User juanCaspa  = createUserWithName("Juan",
                                            "Caspa" ,
                                            "juanCaspa@asquerositos.com",
                                            "elbasurita",
                                            LocalDateTime.of(1990,12,31,18,13));
        User joseTejo   = createUserWithName("Jose",
                                            "Tejo" ,
                                            "josetejo@des.com",
                                            "tjdes",
                                            LocalDateTime.of(1982,8,10,20,12));
        User donBilletin   = createUserWithName("Don",
                                                "Billetin" ,
                                                "donbilletin@havisto.com",
                                                "havistounbilletin",
                                                LocalDateTime.of(1990,1,29,17,10));
        this.userDAO.saveAll(Arrays.asList(juanCaspa,joseTejo,donBilletin));
        List<Event> events = ivanDEvents(ivanDominikow, ivanTamargo, victorDegano, pepeLocura, juanCaspa, joseTejo, donBilletin);
        events.addAll(ivanTEvents(ivanTamargo, victorDegano, pepeLocura,ivanDominikow, juanCaspa, joseTejo, donBilletin));
        events.addAll(victorEvents(victorDegano, pepeLocura, ivanDominikow, ivanTamargo, juanCaspa, joseTejo, donBilletin));
        events.addAll(pepeEvents(pepeLocura, ivanDominikow, ivanTamargo, victorDegano, juanCaspa, joseTejo, donBilletin));
        this.eventService.createAll(events);


        String jsonIvan = json(UserDTO.from(ivanDominikow));
        logger.info(jsonIvan);
        String jsonVictor = json(UserDTO.from(victorDegano));
        logger.info(jsonVictor);
    }


    private String json(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }

    private List<Event> ivanDEvents(User organizerIvan, User ivan, User victor, User pepe, User juanCaspa, User joseTejo, User donBilletin) {
        logger.info("Creating Ivan Dominikow Events");
        List<Event> ivanEvents = new ArrayList<>();
        ivanEvents.add(new Fiesta(
                            "La fiesta de Ivan",
                            organizerIvan,
                            Arrays.asList(
                                    new Guest(ivan),
                                    new Guest(victor),
                                    new Guest(pepe),
                                    new Guest(juanCaspa),
                                    new Guest(joseTejo),
                                    new Guest(donBilletin)),
                            LocalDateTime.now().plusDays(15),
                            EMPTY_LIST,
                            LocalDateTime.now()));

        ivanEvents.add(new Fiesta(
                "IvanFest",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(victor),
                        new Guest(pepe)),
                LocalDateTime.now().plusDays(10),
                EMPTY_LIST,
                LocalDateTime.now().minusDays(1)));

        ivanEvents.add(new Canasta(
                "Canasteando 2.0",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(victor),
                        new Guest(pepe)),
                EMPTY_LIST,
                LocalDateTime.now().minusDays(10)));

        Canasta aCanasta = new Canasta(
                "Canasteando",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(victor),
                        new Guest(pepe)),
                EMPTY_LIST,
                LocalDateTime.now().minusDays(15));
        aCanasta.close();
        ivanEvents.add(aCanasta);

        return ivanEvents;
    }

    private List<Event> ivanTEvents(User organizerIvan, User victor, User pepe, User ivan, User juanCaspa, User joseTejo, User donBilletin) {
        logger.info("Creating Ivan Tamargo Events");
        List<Event> ivanEvents = new ArrayList<>();

        Canasta aCanasta = new Canasta(
                "Canastamargo",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(pepe),
                        new Guest(victor),
                        new Guest(juanCaspa),
                        new Guest(joseTejo),
                        new Guest(donBilletin)),
                EMPTY_LIST,
                LocalDateTime.now().minusDays(5));
        aCanasta.close();
        ivanEvents.add(aCanasta);

        ivanEvents.add(new Canasta(
                            "La Canasta",
                            organizerIvan,
                            Arrays.asList(
                                    new Guest(ivan),
                                    new Guest(pepe),
                                    new Guest(victor)),
                            EMPTY_LIST,
                            LocalDateTime.now().minusDays(2)));

        ivanEvents.add(new Canasta(
                            "The Last Canasta",
                            organizerIvan,
                            Arrays.asList(
                                    new Guest(ivan),
                                    new Guest(pepe),
                                    new Guest(victor),
                                    new Guest(juanCaspa)),
                            EMPTY_LIST,
                            LocalDateTime.now().minusDays(1)));

        Fiesta aFest = new Fiesta(
                        "La Unica Canasta",
                        organizerIvan,
                        Arrays.asList(
                                new Guest(ivan),
                                new Guest(victor),
                                new Guest(pepe)),
                        LocalDateTime.now().plusDays(15),
                        EMPTY_LIST,
                        LocalDateTime.now());
        aFest.close();
        ivanEvents.add(aFest);
        return ivanEvents;
    }

    private List<Event> victorEvents(User organizerVictor, User pepe, User ivanD, User ivanT, User juanCaspa, User joseTejo, User donBilletin) {
        logger.info("Creating Victor Degano Events");
        List<Event> victorEvents = new ArrayList<>();

        Baquita aBaquita = new BaquitaComunitary(
                                "The Little Cow",
                                organizerVictor,
                                Arrays.asList(
                                        new Guest(ivanD),
                                        new Guest(pepe),
                                        new Guest(ivanT),
                                        new Guest(juanCaspa),
                                        new Guest(donBilletin)),
                                EMPTY_LIST,
                                LocalDateTime.now().minusDays(5));
        aBaquita.close();
        victorEvents.add(aBaquita);

        victorEvents.add(new BaquitaComunitary(
                                "The Cow",
                                organizerVictor,
                                Arrays.asList(
                                        new Guest(ivanD),
                                        new Guest(pepe),
                                        new Guest(ivanT)),
                                EMPTY_LIST,
                                LocalDateTime.now().minusDays(1)));

        victorEvents.add(new Canasta(
                                "Black Canasta",
                                organizerVictor,
                                Arrays.asList(
                                        new Guest(ivanD),
                                        new Guest(pepe),
                                        new Guest(ivanT)),
                                EMPTY_LIST,
                                LocalDateTime.now().minusDays(22)));

        victorEvents.add(new Fiesta(
                "Fest",
                organizerVictor,
                Arrays.asList(
                        new Guest(ivanD),
                        new Guest(pepe)),
                LocalDateTime.now().plusDays(10),
                EMPTY_LIST,
                LocalDateTime.now()));
        return victorEvents;
    }

    private List<Event> pepeEvents(User organizerPepe, User ivanD, User ivanT, User victor, User juanCaspa, User joseTejo, User donBilletin) {
        logger.info("Creating Pepe Locura Events");
        List<Event> pepeEvents = new ArrayList<>();

        Baquita aBaquita = new BaquitaRepresentatives(
                "Una vaca Respetable",
                organizerPepe,
                Arrays.asList(
                        new Guest(ivanD),
                        new Guest(victor),
                        new Guest(ivanT),
                        new Guest(juanCaspa),
                        new Guest(joseTejo)),
                EMPTY_LIST,
                LocalDateTime.now().minusDays(2));
        aBaquita.close();
        pepeEvents.add(aBaquita);
        return pepeEvents;
    }

    private User createUserWithName(String name, String lastname,String email, String password, LocalDateTime bornDay) {
        logger.info(String.format("Creating The User %s %s with email %s",name,lastname,email));
        return new User(name,lastname, email, password, bornDay);
    }
}
