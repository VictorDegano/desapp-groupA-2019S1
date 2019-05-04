package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CreateCanastaStrategyTest {

    private CreateCanastaStrategy createCanastaStrategySUT;

    @Before
    public void setUp(){
        createCanastaStrategySUT = new CreateCanastaStrategy();
    }

    @Test
    public void whenAskingIfHandleACreateFiesta_AnswerFalse() {
        //Setup(Given)

        //Exercise(When)
        Boolean handleIt = createCanastaStrategySUT.hasToHandleCreateEvent(EventType.FIESTA);

        //Test(Then)
        assertFalse("Valido que puede encargarse de la creacion de una fiesta y tiene que encargarse de una canasta",
                    handleIt);
    }

    @Test
    public void whenAskingIfHandleACreateCanasta_AnswerTrue() {
        //Setup(Given)

        //Exercise(When)
        Boolean handleIt = createCanastaStrategySUT.hasToHandleCreateEvent(EventType.CANASTA);

        //Test(Then)
        assertTrue("No valido que se encarga de crear una canasta",
                    handleIt);
    }

    @Test
    public void whenCreateAEvent_ItWasCorrectlyCreated() {
        //Setup(Given)
        User aUser = UserBuilder.buildAUser()
                                .withFirstName("Pepe")
                                .build();

        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Good beer = GoodBuilder.buildAGood()
                               .withQuantityForPerson(4)
                               .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withQuantityForPerson(33)
                                      .build();

        Template canastaTemplate = TemplateBuilder.buildATemplate()
                                                  .addGood(beer)
                                                  .addGood(doritoPack)
                                                  .withEventType(EventType.CANASTA)
                                                  .build();

        //Exercise(When)
        Event aNewEvent = createCanastaStrategySUT.createEvent("Canasteando", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), canastaTemplate);

        //Test(Then)
        assertEquals("Pepe", aNewEvent.getOrganizer().getFirstName());
        assertEquals("Canasteando", aNewEvent.getName());
        assertEquals(1, aNewEvent.getGuest().size());
        assertEquals(2, aNewEvent.getGoodsForGuest().size());
        for (Good eachGood : aNewEvent.getGoodsForGuest()) {
            assertEquals("", true,
                        (eachGood.getQuantityForPerson() == 33)
                                || (eachGood.getQuantityForPerson() == 4));
        }

    }
}