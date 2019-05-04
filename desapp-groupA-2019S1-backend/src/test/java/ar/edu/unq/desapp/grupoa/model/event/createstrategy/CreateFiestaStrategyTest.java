package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.event.createstrategy.CreateFiestaStrategy;
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

public class CreateFiestaStrategyTest {

    private CreateFiestaStrategy createFiestaStrategySUT;

    @Before
    public void setUp(){
        createFiestaStrategySUT = new CreateFiestaStrategy();
    }

    @Test
    public void whenAskingIfHandleACreateCanasta_AnswerFalse() {
        //Setup(Given)

        //Exercise(When)
        Boolean handleIt = createFiestaStrategySUT.hasToHandleCreateEvent(EventType.CANASTA);

        //Test(Then)
        assertFalse("Valido que puede encargarse de la creacion de una canasta y tiene que validar al ser una fiesta",
                    handleIt);
    }

    @Test
    public void whenAskingIfHandleACreateFiesta_AnswerTrue() {
        //Setup(Given)

        //Exercise(When)
        Boolean handleIt = createFiestaStrategySUT.hasToHandleCreateEvent(EventType.FIESTA);

        //Test(Then)
        assertTrue("No valido que se encarga de crear una fiesta",
                handleIt);
    }

    @Test
    public void whenCreateAEvent_ItWasCorrectlyCreated() {
        //Setup(Given)
        User aUser = UserBuilder.buildAUser()
                                .withFirstName("Ivan")
                                .build();

        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Good beer = GoodBuilder.buildAGood()
                               .withQuantityForPerson(2)
                               .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withQuantityForPerson(1)
                                      .build();

        Template fiestaTemplate = TemplateBuilder.buildATemplate()
                                                 .addGood(beer)
                                                 .addGood(doritoPack)
                                                 .withEventType(EventType.FIESTA)
                                                 .build();

        //Exercise(When)
        Event aNewEvent = createFiestaStrategySUT.createEvent("Fiesta Rework", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), fiestaTemplate);

        //Test(Then)
        assertEquals("Ivan", aNewEvent.getOrganizer().getFirstName());
        assertEquals("Fiesta Rework", aNewEvent.getName());
        assertEquals(1, aNewEvent.getGuest().size());
        assertEquals(2, aNewEvent.getGoodsForGuest().size());
    }
}