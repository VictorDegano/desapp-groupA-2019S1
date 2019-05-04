package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void ifTryToCreateACanastaEvent_HasbeenCreatedCorrectly() {
        //Setup(Given)
        User aUser = UserBuilder.buildAUser()
                                .withFirstName("Josue")
                                .build();

        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Good beer = GoodBuilder.buildAGood()
                               .withQuantityForPerson(1)
                               .build();

        Template fiestaTemplate = TemplateBuilder.buildATemplate()
                                                 .addGood(beer)
                                                 .withEventType(EventType.CANASTA)
                                                 .build();

        //Exercise(When)
        Event eventCreated = Event.createWithATemplate("The Event", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), fiestaTemplate, EventType.CANASTA);

        //Test(Then)
        assertEquals("Josue", eventCreated.getOrganizer().getFirstName());
        assertEquals(aUser, eventCreated.getOrganizer());
        assertEquals("The Event", eventCreated.getName());
        assertEquals(1, eventCreated.getGuest().size());
        assertEquals(firstGuest, eventCreated.getGuest().get(0));
        assertEquals(1, eventCreated.getGoodsForGuest().size());
        assertEquals(Integer.valueOf(1), eventCreated.getGoodsForGuest().get(0).getQuantityForPerson());
        assertEquals(beer, eventCreated.getGoodsForGuest().get(0));
    }
}