package ar.edu.unq.desapp.grupoa.runner;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.Baquita;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.baquita.LoadedGood;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;
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

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.deposit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;
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
                                                "havistou",
                                                LocalDateTime.of(1990,1,29,17,10));


        takeLoan(juanCaspa.getAccount());
        deposit(juanCaspa.getAccount(),1000);
        takeLoan(joseTejo.getAccount());
        extract(joseTejo.getAccount(),500);
        takeLoan(ivanDominikow.getAccount());
        deposit(ivanDominikow.getAccount(),555);
        takeLoan(victorDegano.getAccount());
        deposit(victorDegano.getAccount(),2000);
        takeLoan(ivanTamargo.getAccount());
        deposit(ivanTamargo.getAccount(),1500);
        takeLoan(pepeLocura.getAccount());
        deposit(pepeLocura.getAccount(),199);

        this.userDAO.saveAll(Arrays.asList(juanCaspa,donBilletin));

        List<Event> events = ivanDEvents(ivanDominikow, ivanTamargo, victorDegano, pepeLocura, juanCaspa, joseTejo, donBilletin);
        events.addAll(ivanTEvents(ivanTamargo, victorDegano, pepeLocura,ivanDominikow, juanCaspa, joseTejo, donBilletin));
        events.addAll(victorEvents(victorDegano, pepeLocura, ivanDominikow, ivanTamargo, juanCaspa, joseTejo, donBilletin));
        events.addAll(pepeEvents(pepeLocura, ivanDominikow, ivanTamargo, victorDegano, juanCaspa, joseTejo, donBilletin));
        this.eventService.createAll(events);
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
        ivanEvents.add(this.buildLaFiestaDeIvan(organizerIvan, ivan, victor, pepe, juanCaspa, joseTejo, donBilletin));

        ivanEvents.add(this.buildIvanFest(organizerIvan, ivan, victor, pepe, juanCaspa, joseTejo, donBilletin));

        ivanEvents.add(this.buildCanasteandoDosPuntoCero(organizerIvan, ivan, victor, pepe, juanCaspa, joseTejo, donBilletin));

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

    private Canasta buildCanasteandoDosPuntoCero(User organizerIvan, User ivan, User victor, User pepe, User juanCaspa, User joseTejo, User donBilletin) {
        CanastaGood chizitos = new CanastaGood();
        chizitos.setName("Chizitos");
        chizitos.setPricePerUnit(50);
        chizitos.setQuantityForPerson(1);
        chizitos.setUserThatOwnsTheGood(ivan);

        CanastaGood palitos = new CanastaGood();
        palitos.setName("Palitos de Queso");
        palitos.setPricePerUnit(20);
        palitos.setQuantityForPerson(3);

        CanastaGood queso = new CanastaGood();
        queso.setName("Queso");
        queso.setPricePerUnit(70);
        queso.setQuantityForPerson(1);
        queso.setUserThatOwnsTheGood(pepe);

        CanastaGood mani = new CanastaGood();
        mani.setName("Mani");
        mani.setPricePerUnit(25);
        mani.setQuantityForPerson(2);
        mani.setUserThatOwnsTheGood(joseTejo);

        Guest victorGuest = new Guest(victor);
        victorGuest.confirmAsistance();

        return new Canasta(
                "Canasteando 2.0",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        victorGuest,
                        new Guest(pepe),
                        new Guest(juanCaspa),
                        new Guest(joseTejo),
                        new Guest(donBilletin)),
                Arrays.asList(chizitos, palitos, queso, mani),
                LocalDateTime.now().minusDays(10));
    }

    private Fiesta buildLaFiestaDeIvan(User organizerIvan, User ivan, User victor, User pepe, User juanCaspa, User joseTejo, User donBilletin) {

        FiestaGood pizza = new FiestaGood();
        pizza.setFinalQuantity(0);
        pizza.setName("Pizza Individual Extra Queso");
        pizza.setPricePerUnit(100);
        pizza.setQuantityForPerson(1);

        FiestaGood cerveza = new FiestaGood();
        cerveza.setFinalQuantity(0);
        cerveza.setName("Cerveza Heineken");
        cerveza.setPricePerUnit(80);
        cerveza.setQuantityForPerson(1);

        FiestaGood papas = new FiestaGood();
        papas.setFinalQuantity(0);
        papas.setName("Bolsa de papas fritas");
        papas.setPricePerUnit(30);
        papas.setQuantityForPerson(2);

        return new Fiesta(
                "IvanFest",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(victor),
                        new Guest(pepe)),
                LocalDateTime.now().plusDays(10),
                Arrays.asList(cerveza, pizza, papas),
                LocalDateTime.now().minusDays(1));
    }

    private Fiesta buildIvanFest(User organizerIvan, User ivan, User victor, User pepe, User juanCaspa, User joseTejo, User donBilletin) {
        FiestaGood cerveza = new FiestaGood();
        cerveza.setFinalQuantity(0);
        cerveza.setName("Cerveza Heineken");
        cerveza.setPricePerUnit(80);
        cerveza.setQuantityForPerson(1);
        FiestaGood pizza = new FiestaGood();
        pizza.setFinalQuantity(0);
        pizza.setName("Pizza Individual Extra Queso");
        pizza.setPricePerUnit(100);
        pizza.setQuantityForPerson(1);
        FiestaGood pizzaDos = new FiestaGood();
        pizzaDos.setFinalQuantity(0);
        pizzaDos.setName("Porcion de Faina");
        pizzaDos.setPricePerUnit(20);
        pizzaDos.setQuantityForPerson(3);
        FiestaGood papas = new FiestaGood();
        papas.setFinalQuantity(0);
        papas.setName("Bolsa de papas fritas");
        papas.setPricePerUnit(30);
        papas.setQuantityForPerson(2);

        return new Fiesta("La fiesta de Ivan",
                organizerIvan,
                Arrays.asList(
                        new Guest(ivan),
                        new Guest(victor),
                        new Guest(pepe),
                        new Guest(juanCaspa),
                        new Guest(joseTejo),
                        new Guest(donBilletin)),
                LocalDateTime.now().plusDays(15),
                Arrays.asList(cerveza, pizza, pizzaDos, papas),
                LocalDateTime.now());
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

        ivanEvents.add(buildLaUnicaFiesta(organizerIvan, victor, pepe, ivan));
        return ivanEvents;
    }

    private Fiesta buildLaUnicaFiesta(User organizerIvan, User victor, User pepe, User ivan) {
        FiestaGood cerveza = new FiestaGood();
        cerveza.setFinalQuantity(0);
        cerveza.setName("Cerveza Quilmes");
        cerveza.setPricePerUnit(70);
        cerveza.setQuantityForPerson(2);

        FiestaGood choripan = new FiestaGood();
        choripan.setFinalQuantity(0);
        choripan.setName("Choripanes");
        choripan.setPricePerUnit(30);
        choripan.setQuantityForPerson(4);

        FiestaGood chinchulines = new FiestaGood();
        chinchulines.setFinalQuantity(0);
        chinchulines.setName("Tira corta de chinchulines");
        chinchulines.setPricePerUnit(35);
        chinchulines.setQuantityForPerson(1);

        FiestaGood morcilla = new FiestaGood();
        morcilla.setFinalQuantity(0);
        morcilla.setName("Morcilla");
        morcilla.setPricePerUnit(40);
        morcilla.setQuantityForPerson(1);

        Fiesta aFest = new Fiesta(
                        "La Unica Canasta",
                        organizerIvan,
                        Arrays.asList(
                                new Guest(ivan),
                                new Guest(victor),
                                new Guest(pepe)),
                        LocalDateTime.now().plusDays(15),
                        Arrays.asList(cerveza, choripan, chinchulines, morcilla),
                        LocalDateTime.now());
        aFest.close();
        return aFest;
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

        victorEvents.add(this.buildTheCow(organizerVictor, pepe, ivanD, ivanT, juanCaspa, joseTejo, donBilletin));

        victorEvents.add(new Canasta(
                                "Black Canasta",
                                organizerVictor,
                                Arrays.asList(
                                        new Guest(ivanD),
                                        new Guest(pepe),
                                        new Guest(ivanT)),
                                EMPTY_LIST,
                                LocalDateTime.now().minusDays(22)));

        victorEvents.add(buildTheFest(organizerVictor, pepe, ivanD));
        return victorEvents;
    }

    private Baquita buildTheCow(User organizerVictor, User pepe, User ivanD, User ivanT, User juanCaspa, User joseTejo, User donBilletin){
        Good sanguMiga = new Good();
        sanguMiga.setName("Sanguches de Miga");
        sanguMiga.setPricePerUnit(25);
        sanguMiga.setQuantityForPerson(5);

        Good porron = new Good();
        porron.setName("Porron Stella Artois");
        porron.setPricePerUnit(50);
        porron.setQuantityForPerson(2);

        Good queso = new Good();
        queso.setName("Queso Reggionato");
        queso.setPricePerUnit(60);
        queso.setQuantityForPerson(1);

        Good pizza = new CanastaGood();
        pizza.setName("Pizza Individual");
        pizza.setPricePerUnit(59);
        pizza.setQuantityForPerson(2);

        Good helado = new Good();
        helado.setName("Kilo de Helado");
        helado.setPricePerUnit(140);
        helado.setQuantityForPerson(1);

        Good factura = new Good();
        factura.setName("Facturas");
        factura.setPricePerUnit(25);
        factura.setQuantityForPerson(4);

        Good milhojas = new Good();
        milhojas.setName("Milhojas");
        milhojas.setPricePerUnit(25);
        milhojas.setQuantityForPerson(4);

        Good fernet = new Good();
        fernet.setName("Fernet");
        fernet.setPricePerUnit(260);
        fernet.setQuantityForPerson(1);

        Guest ivandDGuest = new Guest(ivanD);
        ivandDGuest.cancelInvitation();
        Guest pepeGuest = new Guest(pepe);
        pepeGuest.confirmAsistance();
        Guest ivanTGuest =new Guest(ivanT);
        ivanTGuest.confirmAsistance();

        Baquita theCowBaquita = new BaquitaComunitary(
                "The Cow",
                organizerVictor,
                Arrays.asList(
                        ivandDGuest,
                        pepeGuest,
                        ivanTGuest),
                Arrays.asList(sanguMiga, porron, queso, pizza, helado, factura, fernet, milhojas),
                LocalDateTime.now().minusDays(1));

        return theCowBaquita;

    }

    private Fiesta buildTheFest(User organizerVictor, User pepe, User ivanD) {
        FiestaGood cerveza = new FiestaGood();
        cerveza.setFinalQuantity(0);
        cerveza.setName("Cerveza Patagonia");
        cerveza.setPricePerUnit(120);
        cerveza.setQuantityForPerson(5);

        FiestaGood pizza = new FiestaGood();
        pizza.setFinalQuantity(0);
        pizza.setName("Pizza Chica de 4 Quesos");
        pizza.setPricePerUnit(45);
        pizza.setQuantityForPerson(6);

        return new Fiesta(
                "The Fest",
                organizerVictor,
                Arrays.asList(
                        new Guest(ivanD),
                        new Guest(pepe)),
                LocalDateTime.now().plusDays(10),
                Arrays.asList(cerveza, pizza),
                LocalDateTime.now());
    }

    private List<Event> pepeEvents(User organizerPepe, User ivanD, User ivanT, User victor, User juanCaspa, User joseTejo, User donBilletin) {
        logger.info("Creating Pepe Locura Events");
        List<Event> pepeEvents = new ArrayList<>();

        pepeEvents.add(this.buildUnaVacaRespetable(organizerPepe, ivanD, ivanT, victor, juanCaspa, joseTejo, donBilletin));
        return pepeEvents;
    }

    private Baquita buildUnaVacaRespetable(User organizerPepe, User ivanD, User ivanT, User victor, User juanCaspa, User joseTejo, User donBilletin){
        Guest ivanDG = new Guest(ivanD);
        Guest ivanTG = new Guest(ivanT);
        Guest victorG = new Guest(victor);
        victorG.confirmAsistance();
        Guest juanCaspaG = new Guest(juanCaspa);
        Guest joseTejoG = new Guest(joseTejo);

        Good cerveza = new Good();
        cerveza.setName("Cerveza Heineken");
        cerveza.setPricePerUnit(65);
        cerveza.setQuantityForPerson(2);
        Good pizza = new Good();
        pizza.setName("Pizza 4 Quesos");
        pizza.setPricePerUnit(130);
        pizza.setQuantityForPerson(1);
        Good papas = new Good();
        papas.setName("Detodito");
        papas.setPricePerUnit(55);
        papas.setQuantityForPerson(2);

        BaquitaRepresentatives aBaquita = new BaquitaRepresentatives(
                "Una vaca Respetable",
                organizerPepe,
                Arrays.asList(ivanDG,ivanTG,victorG,juanCaspaG,joseTejoG),
                Arrays.asList(cerveza, pizza, papas),
                LocalDateTime.now().minusDays(2));

        aBaquita.addRepresentative(victorG);
        aBaquita.addRepresentative(ivanTG);

        LoadedGood cervezaGood = new LoadedGood(victorG,cerveza);
        LoadedGood papasGood = new LoadedGood(ivanTG,papas);
        aBaquita.loadGood(cervezaGood);
        aBaquita.loadGood(papasGood);

        return aBaquita;
    }

    private User createUserWithName(String name, String lastname,String email, String password, LocalDateTime bornDay) {
        logger.info(String.format("Creating The User %s %s with email %s",name,lastname,email));
        return new User(name,lastname, email, password, bornDay);
    }
}
