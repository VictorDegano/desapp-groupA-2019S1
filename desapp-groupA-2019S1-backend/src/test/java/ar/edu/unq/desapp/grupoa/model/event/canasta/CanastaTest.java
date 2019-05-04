package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.CanastaCloseException;
import ar.edu.unq.desapp.grupoa.exception.event.GoodAlreadyOwnedException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CloseCanasta;
import ar.edu.unq.desapp.grupoa.exception.event.OwnAGoodWithAnUnconfirmedGuestException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUserWithName;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CanastaTest {
    private CanastaGood beer;
    private User userThatCreateTheCanasta;
    @Before
    public void setUp(){
        beer = new CanastaGood("Beer",10,1);
        userThatCreateTheCanasta = randomUserWithName("Ivan");
    }

    @Test
    public void whenAnUserCreatesACanastaWithANameTheGoodsListAndTheGuestsListAreEmpty(){

        //Exercise(When)
        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .build();
        //Test(Then)
        assertEquals("The Guests list should be empty at the initialization!",
                0,
                newCanasta.getGuest().size());

        assertEquals("The Goods list should be empty at the initialization!",
                0,
                newCanasta.getGoodsForGuest().size());

        assertEquals("The organizer is wrong!",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());

        assertEquals("Canasta name is WRONG!",
                "Canastita",
                newCanasta.getName());
    }

    @Test
    public void whenAnUserCreatesACanastaWithANameTheGoodsListAndTheGuestsListAreTheOnesProvided(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");
        User userJose = randomUserWithName("Jose");

        Guest guestCarlos = GuestBuilder.buildAGuest()
        .withUser(userCarlos)
        .build();
        Guest guestJose = GuestBuilder.buildAGuest()
        .withUser(userJose)
        .build();

        //Exercise(When)
        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGuest(guestJose)
                .addGood(beer)
                .build();

        //Test(Then)
        assertEquals("La lista de invitados tiene 2 invitados",
                2,
                newCanasta.getGuest().size());

        assertEquals("La lista de gastos tiene 1 item",
                1,
                newCanasta.getGoodsForGuest().size());

        assertEquals("el organizador de la canasta es el usuario que la creo",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());
    }

    @Test
    public void whenAnUserCreatesACanastaTheInitialStateIsOpen(){

        //Exercise(When)
        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .build();

        //Test(Then)
        assertTrue("El estado de la Canasta es en preparacion cuando se inicializa",
                newCanasta.getState().isInPreparationCanasta());

        assertFalse("El estado de la Canasta no es cerrado",
                newCanasta.getState().isCloseCanasta());

    }

    @Test
    public void whenAUserIsConfirmInACanastaTheGuestThatRepresentsThatUserIsConfirmed(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .build();
        //Exercise(When)
        newCanasta.confirmUser(userCarlos);

        //Test(Then)
        assertEquals("La lista de invitados tiene 1 invitados",
                1,
                newCanasta.getGuest().size());

        assertEquals("La lista de gastos tiene 0 items",
                0,
                newCanasta.getGoodsForGuest().size());

        assertEquals("el organizador de la canasta es el usuario que la creo",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());

        assertEquals("el guestCarlos esta confirmado", InvitationState.ACCEPTED,
                    guestCarlos.getConfirmAsistance());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void whenAUserIsConfirmInACanastaAndItIsNotInTheGuestListTheConfirmationThrowsConfirmAsistanceException(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");
        User userJose = randomUserWithName("Jose");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .build();
        //Exercise(When)
        newCanasta.confirmUser(userJose);

    }

    @Test(expected = ConfirmAsistanceException.class)
    public void whenAUserIsConfirmInACanastaAndItsCloseThrowsConfirmAsistanceException(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .withClosedState()
                .build();
        //Exercise(When)
        newCanasta.confirmUser(userCarlos);

    }

    @Test
    public void whenACanastaAssignAGoodToAnUserThatGoodBelongsToThatUser(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGood(beer)
                .build();
        newCanasta.confirmUser(userCarlos);
        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);

        //Test(Then)
        assertEquals("el guestCarlos esta confirmado",
                InvitationState.ACCEPTED,
                guestCarlos.getConfirmAsistance());
        assertEquals("el good beer no es de userCarlos",
                beer.getUserThatOwnsTheGood(),
                userCarlos);
    }

    @Test(expected = OwnAGoodWithAnUnconfirmedGuestException.class)
    public void whenACanastaAssignAGoodToAnUserThatIsNotConfirmedIsThrowsOwnAGoodWithAnUnconfirmedGuestException(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGood(beer)
                .build();
        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);

    }

    @Test(expected = CanastaCloseException.class)
    public void whenACanastaAssignAGoodToAnUserAndTheCanastaIsCloseItThrowsCanastaCloseException(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGood(beer)
                .build();

        newCanasta.confirmUser(userCarlos);
        newCanasta.closeCanasta();
        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);

    }

    @Test(expected = GoodAlreadyOwnedException.class)
    public void whenACanastaAssignAGoodToAnUserAndOtherUserWantToOwnTheSameGoodItThrowsGoodAlreadyOwnedException(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");
        User userGaby = randomUserWithName("Gaby");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();
        Guest guestGaby = GuestBuilder.buildAGuest()
                .withUser(userGaby)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGuest(guestGaby)
                .addGood(beer)
                .build();
        newCanasta.confirmUser(userCarlos);
        newCanasta.confirmUser(userGaby);

        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);
        newCanasta.ownAGood(userGaby,beer);

    }

    @Test
    public void whenACanastaIsClosedAllThePendingGuestConvertsToCancelled(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");
        User userGaby = randomUserWithName("Gaby");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .withConfirmation(InvitationState.PENDING)
                .build();
        Guest guestGaby = GuestBuilder.buildAGuest()
                .withUser(userGaby)
                .withConfirmation(InvitationState.ACCEPTED)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGuest(guestGaby)
                .addGood(beer)
                .build();

        //Exercise(When)
        newCanasta.closeCanasta();
        //Test(Then)
        assertEquals("el guestGaby no esta ACCEPTED,y deberia estarlo porque se lo confirmo",
                InvitationState.ACCEPTED,
                guestGaby.getConfirmAsistance());
        assertEquals("el estado de la invitacion del guestCarlos deberia estar cancelada porque se cerro la canasta ",
                InvitationState.CANCELLED,
                guestCarlos.getConfirmAsistance());

    }

    @Test
    public void whenACanastaAssignAGoodToAnUserAndTheCanastaClosesTheUserPayTheGood(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .withConfirmation(InvitationState.PENDING)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(randomUserWithName("Ivan"))
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGood(beer)
                .build();

        newCanasta.confirmUser(userCarlos);
        newCanasta.ownAGood(userCarlos,beer);
        userCarlos.deposit(200);
        assertEquals("the initial balance should be 200",
                Integer.valueOf(200),
                userCarlos.balance());

        //Exercise(When)
        newCanasta.closeCanasta();
        //Test(Then)
        assertEquals("the user account has the same money!!",
                Integer.valueOf(190),
                userCarlos.balance());

    }

    @Test
    public void whenACanastaClosesTheOrganizerPaysForAllTheGoodsThatWereNotBeOwned(){
        //Setup(Given)
        User userCarlos = randomUserWithName("Carlos");

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .withConfirmation(InvitationState.PENDING)
                .build();

        Canasta newCanasta = CanastaBuilder.buildCanasta()
                .withOrganizer(userThatCreateTheCanasta)
                .withName("Canastita")
                .addGuest(guestCarlos)
                .addGood(beer)
                .build();
        newCanasta.confirmUser(userCarlos);

        userThatCreateTheCanasta.deposit(200);
        assertEquals("the initial balance should be 200",
                Integer.valueOf(200),
                userThatCreateTheCanasta.balance());

        //Exercise(When)
        newCanasta.closeCanasta();
        //Test(Then)
        assertEquals("the user account has the same money!!",
                Integer.valueOf(190),
                userThatCreateTheCanasta.balance());

    }

}
