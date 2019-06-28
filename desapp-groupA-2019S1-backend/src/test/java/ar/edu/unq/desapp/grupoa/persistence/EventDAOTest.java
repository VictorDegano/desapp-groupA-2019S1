package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EventDAOTest {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void whenAskForTheLastEventsOfAUserAndHaveTwoButOneAsOrganizerAndTheOtherAsGuestButAreOthersEventsOfOthersUsers_OnlyThatTwoEventsAreRetrievedInDescOrder(){
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

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                    .withCreationDate(LocalDateTime.now().minusDays(10))
                                    .withOrganizer(aOrganizer2)
                                    .withName("Pepellooza")
                                    .withOpenState()
                                    .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                                        .withCreationDate(LocalDateTime.now().minusDays(1))
                                        .withName("Canasteando")
                                        .withOrganizer(aOrganizer2)
                                        .addGuest(GuestBuilder.buildAGuest().withUser(aOrganizer1).build())
                                        .withClosedState()
                                        .build();

        Fiesta fiesta2 = FiestaBuilder.buildAFiesta()
                                    .withCreationDate(LocalDateTime.now().plusDays(10))
                                    .withOrganizer(aOrganizer1)
                                    .withName("Pepellooza")
                                    .withOpenState()
                                    .build();

        eventDAO.saveAll(Arrays.asList(fiesta,canasta1,fiesta2));

        //Exercise
        List<Event> eventsOfIvan = eventDAO.getLastEventsForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 2 eventos",2,eventsOfIvan.size());
        assertEquals("Tendria que ser la fiesta la primera",fiesta2.getId(),eventsOfIvan.get(0).getId());
        assertEquals("Tendria que ser la canasta la segunda",canasta1.getId(),eventsOfIvan.get(1).getId());
    }

    @Test
    public void whenAskForTheLastEventsOfAUserAndHaveTwoButAreOthersEventsOfOthersUsers_OnlyThatTwoEventsIsRetrievedInDescOrder(){
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

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                .withCreationDate(LocalDateTime.now().minusDays(10))
                .withOrganizer(aOrganizer2)
                .withName("Pepellooza")
                .withOpenState()
                .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                .withCreationDate(LocalDateTime.now().minusDays(1))
                .withName("Canasteando")
                .withOrganizer(aOrganizer1)
                .withClosedState()
                .build();

        Fiesta fiesta2 = FiestaBuilder.buildAFiesta()
                                    .withCreationDate(LocalDateTime.now().plusDays(10))
                                    .withOrganizer(aOrganizer1)
                                    .withName("Pepellooza")
                                    .withOpenState()
                                    .build();

        eventDAO.saveAll(Arrays.asList(fiesta,canasta1,fiesta2));

        //Exercise
        List<Event> eventsOfIvan = eventDAO.getLastEventsForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 2 eventos",2,eventsOfIvan.size());
        assertEquals("Tendria que ser la fiesta la primera",fiesta2.getId(),eventsOfIvan.get(0).getId());
        assertEquals("Tendria que ser la canasta la segunda",canasta1.getId(),eventsOfIvan.get(1).getId());
    }

    @Test
    public void whenAskForTheEventsInProgressOfAUserAndHaveOnlyOneButAreOthersEventsOfOthersUsers_OnlyTheirEventIsRetrieved(){
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

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                     .withOrganizer(aOrganizer2)
                                     .withName("Pepellooza")
                                     .withOpenState()
                                     .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                                         .withName("Canasteando")
                                         .withOrganizer(aOrganizer1)
                                         .withOpenState()
                                         .build();

        eventDAO.saveAll(Arrays.asList(fiesta,canasta1));

        //Exercise
        List<Event> eventsOfIvan = eventDAO.getEventsInProgressForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 1 evento nada mas",1,eventsOfIvan.size());
        assertEquals("Deberia haber traido solo el evento Canasteand","Canasteando",eventsOfIvan.get(0).getName());
        assertEquals("Los organizadores no son los mismos!",aOrganizer1.getId(),eventsOfIvan.get(0).getOrganizer().getId());
    }

    @Test
    public void whenAskForTheEventsInProgressOfAUserAndTheOnlyHisHasIsClosed_NothingAreRetrieved(){
        //Setup
        User aOrganizer1 = UserBuilder.buildAUser()
                .withFirstName("Ivan")
                .withLastName("Dominikow")
                .withMail("ivancito@saraza.com")
                .withBornDay(LocalDateTime.of(2019,10,30,20,00))
                .withPassword("laSaraza")
                .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                .withName("Canasteando")
                .withOrganizer(aOrganizer1)
                .withClosedState()
                .build();

        eventDAO.save(canasta1);

        //Exercise
        List<Event> eventsOfIvan = eventDAO.getEventsInProgressForUser(aOrganizer1.getId());

        //Test(
        assertTrue("Tiene que estar vacia, el unico evento que posee esta cerrado", eventsOfIvan.isEmpty());
    }

    @Test
    public void whenAskForTheEventsInProgressOfAUserAndHaveOnlyOneWhereIsOrganizerOneWhereIsAGuestAndAreOthersEventsOfOthersUsers_OnlyThatsEventsAreRetrieved(){
        //Setup(Given)
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

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                    .withOrganizer(aOrganizer2)
                                    .withName("Pepellooza")
                                    .withOpenState()
                                    .build();

        Canasta canasta1 = CanastaBuilder.buildCanasta()
                                        .withName("Canasteando")
                                        .withOrganizer(aOrganizer1)
                                        .withOpenState()
                                        .build();

        Canasta canasta2 = CanastaBuilder.buildCanasta()
                                         .withName("Jugando Canasta")
                                         .withOrganizer(aOrganizer2)
                                         .addGuest(GuestBuilder.buildAGuest().withUser(aOrganizer1).build())
                                         .withOpenState()
                                         .build();

        eventDAO.saveAll(Arrays.asList(fiesta,canasta1, canasta2));

        //Exercise
        List<Event> eventsOfIvan = eventDAO.getEventsInProgressForUser(aOrganizer1.getId());

        //Test(
        assertEquals("Tendria que haber traido 2 eventos",2,eventsOfIvan.size());
        assertEquals("Deberia ser el evento Canasteando","Canasteando",eventsOfIvan.get(0).getName());
        assertEquals("Los organizadores no son los mismos!",aOrganizer1.getId(),eventsOfIvan.get(0).getOrganizer().getId());
        assertEquals("Deberia ser el evento Jugando Canasta","Jugando Canasta",eventsOfIvan.get(1).getName());
    }
}