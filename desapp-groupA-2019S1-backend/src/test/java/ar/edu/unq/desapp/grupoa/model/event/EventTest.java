package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.exception.event.InvitationException;
import ar.edu.unq.desapp.grupoa.exception.event.InvitationLimitException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.*;
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

        Good beer = CanastaGoodBuilder.buildAGood()
                                      .withQuantityForPerson(1)
                                      .build();

        Template fiestaTemplate = TemplateBuilder.buildATemplate()
                                                 .addGood(beer)
                                                 .withEventType(EventType.CANASTA)
                                                 .build();

        LocalDateTime creationDate = LocalDateTime.now();

        //Exercise(When)
        Event eventCreated = Event.createWithATemplate("The Event", aUser, Arrays.asList(firstGuest), LocalDateTime.now(), fiestaTemplate, EventType.CANASTA, creationDate);

        //Test(Then)
        assertEquals("Josue", eventCreated.getOrganizer().getFirstName());
        assertEquals(aUser, eventCreated.getOrganizer());
        assertEquals("The Event", eventCreated.getName());
        assertEquals(1, eventCreated.getGuest().size());
        assertEquals(firstGuest, eventCreated.getGuest().get(0));
        assertEquals(1, eventCreated.getGoodsForGuest().size());
        assertEquals(Integer.valueOf(1), eventCreated.getGoodsForGuest().get(0).getQuantityForPerson());
        assertEquals(beer, eventCreated.getGoodsForGuest().get(0));
        assertEquals(creationDate, eventCreated.getCreationDate());
    }

    @Test(expected = InvitationLimitException.class)
    public void whenTryToinviteAUserAndTheEventIsClosed_GetAException(){
        //Setup(Given)
        Event anyEvent = FiestaBuilder.buildAFiesta().withClosedState().build();

        User anyUser = UserBuilder.buildAUser().build();

        //Exercise(When)
        anyEvent.inviteUser(anyUser);

        //Test(Then)
    }

    @Test(expected = InvitationException.class)
    public void whenTryToInviteAUserAndTheEventAlreadyHaveAtTheUserHasAGuest_GetAException(){
        //Setup(Given)
        User aUser = UserBuilder.buildAUser()
                                .withFirstName("Pepe")
                                .withLastName("Locura")
                                .build();

        Guest guest = GuestBuilder.buildAGuest()
                                  .withUser(aUser)
                                  .build();

        Event anyEvent = FiestaBuilder.buildAFiesta()
                                      .addGuest(guest)
                                      .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
                                      .withOpenState()
                                      .build();

        //Exercise(When)
        anyEvent.inviteUser(aUser);

        //Test(Then)
    }

    @Test
    public void whenTryToInviteAUserAndTheEventIsOpenAndDontHaveItAsAGuest_TheUserIsInvited(){
        //Setup(Given)
        User aUser = UserBuilder.buildAUser()
                                .build();

        Event anyEvent = FiestaBuilder.buildAFiesta()
                                      .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
                                      .withOpenState()
                                      .build();

        //Exercise(When)
        anyEvent.inviteUser(aUser);

        //Test(Then)
        assertEquals("No invito al usuario y el evento esta abierto y sin el usuario como invitado.",
                     1,
                      anyEvent.getGuest().size());
    }

    @Test
    public void whenAskAnEventIfCanInviteUsersAndIsClosed_GetTrue(){
        //Setup(Given)
        Event anyEvent = CanastaBuilder.buildCanasta()
                                       .withClosedState()
                                       .build();

        //Exercise(When)
        Boolean canInvite = anyEvent.canInviteUser();

        //Test(Then)
        assertFalse("Tendria que haber sido False, el evento que se usa para testear se le puso un estado de cerrado!",
                    canInvite);
    }
}