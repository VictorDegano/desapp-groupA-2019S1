package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static ar.edu.unq.desapp.grupoa.utils.builder.FiestaGoodBuilder.*;
import static ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder.*;
import static ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder.*;
import static ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateBaquitaStrategyTest {

    private CreateBaquitaComunitariaStrategy createBaquitaComunitariaStrategy;

    private CreateBaquitaRepresentantesStrategy createBaquitaRepresentantesStrategy;

    //TODO: Esto esta lleno de duplicacion. Con el resto de los strategy test tambien. Hay que refactorizar

    @Before
    public void setUp(){
        createBaquitaComunitariaStrategy    = new CreateBaquitaComunitariaStrategy();
        createBaquitaRepresentantesStrategy = new CreateBaquitaRepresentantesStrategy();
    }

    @Test
    public void whenAskedIfItCanHandleAComunitaryBaquita_AnswerTrue() {
        assertTrue(createBaquitaComunitariaStrategy.hasToHandleCreateEvent(EventType.BAQUITA_COMUNITARY));
    }

    @Test
    public void whenAskedIfItCanHandleARepresentativesBaquita_AnswerTrue() {
        assertTrue(createBaquitaRepresentantesStrategy.hasToHandleCreateEvent(EventType.BAQUITA_REPRESENTATIVES));
    }

    @Test
    public void whenCreateBaquitaComunitary_ItWasCorrectlyCreated() {
        //Setup(Given)
        User aUser = buildAUser()
                .withFirstName("Ivan")
                .build();

        Guest firstGuest = buildAGuest().build();

        Good beer = buildAGood()
                .withQuantityForPerson(2)
                .build();

        Good doritoPack  = buildAGood()
                .withQuantityForPerson(1)
                .build();

        Template baquitaComunitariaTemplate = buildATemplate()
                .addGood(beer)
                .addGood(doritoPack)
                .withEventType(EventType.BAQUITA_COMUNITARY)
                .build();

        LocalDateTime creationDate = LocalDateTime.now();

        //Exercise(When)
        Event aNewEvent = createBaquitaComunitariaStrategy.createEvent("Pepin fiesta", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), baquitaComunitariaTemplate,creationDate);

        //Test(Then)
        assertEquals("Ivan", aNewEvent.getOrganizer().getFirstName());
        assertEquals("Pepin fiesta", aNewEvent.getName());
        assertEquals(1, aNewEvent.getGuest().size());
        assertEquals(2, aNewEvent.getGoodsForGuest().size());
        assertEquals(creationDate, aNewEvent.getCreationDate());
    }

    @Test
    public void whenCreateBaquitaRepresentantes_ItWasCorrectlyCreated() {
        //Setup(Given)
        User aUser = buildAUser()
                .withFirstName("Ivan")
                .build();

        Guest firstGuest = buildAGuest().build();

        Good beer = buildAGood()
                .withQuantityForPerson(2)
                .build();

        Good doritoPack  = buildAGood()
                .withQuantityForPerson(1)
                .build();

        Template baquitaRepresentantesTemplate = buildATemplate()
                .addGood(beer)
                .addGood(doritoPack)
                .withEventType(EventType.BAQUITA_REPRESENTATIVES)
                .build();

        LocalDateTime creationDate = LocalDateTime.now();

        //Exercise(When)
        Event aNewEvent = createBaquitaComunitariaStrategy.createEvent("Pepin fiesta", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), baquitaRepresentantesTemplate, creationDate);

        //Test(Then)
        assertEquals("Ivan", aNewEvent.getOrganizer().getFirstName());
        assertEquals("Pepin fiesta", aNewEvent.getName());
        assertEquals(1, aNewEvent.getGuest().size());
        assertEquals(2, aNewEvent.getGoodsForGuest().size());
        assertEquals(creationDate, aNewEvent.getCreationDate());
    }

}
