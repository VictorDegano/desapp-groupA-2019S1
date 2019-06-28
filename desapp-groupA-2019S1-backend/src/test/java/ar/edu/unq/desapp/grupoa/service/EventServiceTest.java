package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.Randomizer;
import ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.UserDataHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDAO eventDAO;

    @Test
    public void whenAskTheLastEventsOfAUser_TheirAreRetrieved(){
        //Setup
        User aOrganizer1 = UserBuilder.buildAUser()
                .withFirstName("Ivan")
                .withLastName("Dominikow")
                .withMail("ivancito@saraza.com")
                .withBornDay(LocalDateTime.of(2019,10,30,20,00))
                .withPassword("laSaraza")
                .build();

        Fiesta fiesta1 = FiestaBuilder.buildAFiesta()
                .withOrganizer(aOrganizer1)
                .withCreationDate(LocalDateTime.now().minusDays(1))
                .withName("La Fiesta loca")
                .withOpenState()
                .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                .withName("Canasteando")
                .withCreationDate(LocalDateTime.now().plusDays(10))
                .withOrganizer(aOrganizer1)
                .withOpenState()
                .build();

        eventDAO.saveAll(Arrays.asList(fiesta1,canasta1));

        //Exercise
        List<Event> eventsOfIvan = eventService.getLastEventsForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 2 eventos nada mas",2,eventsOfIvan.size());
        assertEquals(canasta1.getId(),eventsOfIvan.get(0).getId());
        assertEquals(fiesta1.getId(),eventsOfIvan.get(1).getId());
    }

    @Test
    public void whenCreateAFiesta_ItsSavedCorrectly() {
        //Setup(Given)
        LocalDateTime timeOfFiesta = LocalDateTime.of(2018,12,20,22,55,10);

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                     .withOrganizer(Randomizer.randomUserWithName("Jose"))
                                     .withLimitConfirmationDateTime(timeOfFiesta)
                                     .withName("La Fiesta loca")
                                     .withConfirmations(0)
                                     .build();

        //Exercise(When)
        Integer id = this.eventService.create(fiesta);

        //Test(Then)
        Event fiestaReturned = this.eventService.getById(id);

        assertEquals(fiesta.getId(), fiestaReturned.getId());
        assertEquals(timeOfFiesta, fiestaReturned.getLimitConfirmationDateTime());
        assertEquals("La Fiesta loca", fiestaReturned.getName());
        assertEquals(Integer.valueOf(0), fiestaReturned.getConfirmations());
    }

    @Test
    public void whenCreateAListOfFiestas_AllAreSavedCorrectly() {
        //Setup(Given)
        LocalDateTime timeOfFiesta = LocalDateTime.of(2012,11,23,12,23,33);

        Fiesta fiesta1 = FiestaBuilder.buildAFiesta()
                                      .withOrganizer(Randomizer.randomUserWithName("Jose"))
                                      .withLimitConfirmationDateTime(timeOfFiesta)
                                      .withName("La Fiesta loca")
                                      .withConfirmations(0)
                                      .build();

        Fiesta fiesta2 = FiestaBuilder.buildAFiesta()
                                      .withOrganizer(Randomizer.randomUserWithName("Jose"))
                                      .withLimitConfirmationDateTime(timeOfFiesta)
                                      .withName("Pepellooza")
                                      .withConfirmations(1590)
                                      .build();

        //Exercise(When)
        this.eventService.createAll(Arrays.asList(fiesta1,fiesta2));

        //Test(Then)
        Event fiesta1Returned = this.eventService.getById(fiesta1.getId());
        Event fiesta2Returned = this.eventService.getById(fiesta2.getId());

        assertEquals(fiesta1.getId(), fiesta1Returned.getId());
        assertEquals(timeOfFiesta, fiesta1Returned.getLimitConfirmationDateTime());
        assertEquals("La Fiesta loca", fiesta1Returned.getName());
        assertEquals(Integer.valueOf(0), fiesta1Returned.getConfirmations());

        assertEquals(fiesta2.getId(), fiesta2Returned.getId());
        assertEquals(timeOfFiesta, fiesta2Returned.getLimitConfirmationDateTime());
        assertEquals("Pepellooza", fiesta2Returned.getName());
        assertEquals(Integer.valueOf(1590), fiesta2Returned.getConfirmations());
    }

    @Test
    public void whenAskForTheEventsInProgressOfAUserAndHaveSomeEvents_AreRetrieved(){
        //Setup
        User aOrganizer1 = UserBuilder.buildAUser()
                                    .withFirstName("Ivan")
                                    .withLastName("Dominikow")
                                    .withMail("ivancito@saraza.com")
                                    .withBornDay(LocalDateTime.of(2019,10,30,20,00))
                                    .withPassword("laSaraza")
                                    .build();

        User aOrganizer2 = UserBuilder.buildAUser()
                                    .withFirstName("Jose")
                                    .withLastName("lastoma")
                                    .withMail("lastomajose@saraza.com")
                                    .withBornDay(LocalDateTime.of(2010,1,11,20,00))
                                    .withPassword("teLaTomasteTodaChinwenwencha")
                                    .build();

        Fiesta fiesta1 = FiestaBuilder.buildAFiesta()
                                    .withOrganizer(aOrganizer1)
                                    .withName("La Fiesta loca")
                                    .withOpenState()
                                    .build();

        Fiesta fiesta2 = FiestaBuilder.buildAFiesta()
                                    .withOrganizer(aOrganizer2)
                                    .withName("Pepellooza")
                                    .withOpenState()
                                    .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                                         .withName("Canasteando")
                                         .withOrganizer(aOrganizer1)
                                         .withOpenState()
                                         .build();

        eventDAO.saveAll(Arrays.asList(fiesta1,fiesta2,canasta1));

        //Exercise
        List<Event> eventsOfIvan = eventService.getEventsInProgressForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 2 eventos nada mas",2,eventsOfIvan.size());
    }

}