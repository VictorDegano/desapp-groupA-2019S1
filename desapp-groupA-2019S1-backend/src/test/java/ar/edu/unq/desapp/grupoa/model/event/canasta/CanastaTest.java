package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.CanastaCloseException;
import ar.edu.unq.desapp.grupoa.exception.event.GoodAlreadyOwnedException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CloseCanasta;

import ar.edu.unq.desapp.grupoa.exception.event.OwnAGoodWithAnUnconfirmedGuestException;

import ar.edu.unq.desapp.grupoa.model.user.User;
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
    private List<CanastaGood> listOfGoods;
    @Before
    public void setUp(){
        beer = new CanastaGood("Beer",10,1);
        listOfGoods = new ArrayList<>();
    }

    @Test
    public void whenAnUserCreatesACanastaWithANameTheGoodsListAndTheGuestsListAreEmpty(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");

        //Exercise(When)
        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta);

        //Test(Then)
        assertEquals("La lista de invitados esta vacia",
                0,
                newCanasta.getGuests().size());

        assertEquals("La lista de gastos esta vacia",
                0,
                newCanasta.getGoods().size());

        assertEquals("el organizador de la canasta es el usuario que la creo",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());

        assertEquals("el nombre de la canasta es el correcto",
                "Canastita",
                newCanasta.getName());
    }

    @Test
    public void whenAnUserCreatesACanastaWithANameTheGoodsListAndTheGuestsListAreTheOnesProvided(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        User userJose = randomUserWithName("Jose");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
        .withUser(userCarlos)
        .build();
        Guest guestJose = GuestBuilder.buildAGuest()
        .withUser(userJose)
        .build();

        listOfGuests.add(guestCarlos);
        listOfGuests.add(guestJose);

        listOfGoods.add(beer);

        //Exercise(When)
        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);

        //Test(Then)
        assertEquals("La lista de invitados tiene 2 invitados",
                2,
                newCanasta.getGuests().size());

        assertEquals("La lista de gastos tiene 1 item",
                1,
                newCanasta.getGoods().size());

        assertEquals("el organizador de la canasta es el usuario que la creo",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());
    }

    @Test
    public void whenAnUserCreatesACanastaTheInitialStateIsOpen(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");

        //Exercise(When)
        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta);

        //Test(Then)
        assertTrue("El estado de la Canasta es en preparacion cuando se inicializa",
                newCanasta.getState().isInPreparationCanasta());

        assertFalse("El estado de la Canasta no es cerrado",
                newCanasta.getState().isCloseCanasta());

    }

    @Test
    public void whenAUserIsConfirmInACanastaTheGuestThatRepresentsThatUserIsConfirmed(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        //Exercise(When)
        newCanasta.confirmUser(userCarlos);

        //Test(Then)
        assertEquals("La lista de invitados tiene 1 invitados",
                1,
                newCanasta.getGuests().size());

        assertEquals("La lista de gastos tiene 0 items",
                0,
                newCanasta.getGoods().size());

        assertEquals("el organizador de la canasta es el usuario que la creo",
                userThatCreateTheCanasta.getFirstName(),
                newCanasta.getOrganizer().getFirstName());

        assertEquals("el guestCarlos esta confirmado", InvitationState.ACCEPTED,
                    guestCarlos.getConfirmAsistance());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void whenAUserIsConfirmInACanastaAndItIsNotInTheGuestListTheConfirmationThrowsConfirmAsistanceException(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        User userJose = randomUserWithName("Jose");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        //Exercise(When)
        newCanasta.confirmUser(userJose);

    }

    @Test(expected = ConfirmAsistanceException.class)
    public void whenAUserIsConfirmInACanastaAndItsCloseThrowsConfirmAsistanceException(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        newCanasta.setState(new CloseCanasta());
        //Exercise(When)
        newCanasta.confirmUser(userCarlos);

    }

    @Test
    public void whenACanastaAssignAGoodToAnUserThatGoodBelongsToThatUser(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
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
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = new Guest(userCarlos);

        listOfGuests.add(guestCarlos);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);

    }

    @Test(expected = CanastaCloseException.class)
    public void whenACanastaAssignAGoodToAnUserAndTheCanastaIsCloseItThrowsCanastaCloseException(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = new Guest(userCarlos);

        listOfGuests.add(guestCarlos);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        newCanasta.confirmUser(userCarlos);
        newCanasta.setState(new CloseCanasta());
        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);

    }

    @Test(expected = GoodAlreadyOwnedException.class)
    public void whenACanastaAssignAGoodToAnUserAndOtherUserWantToOwnTheSameGoodItThrowsGoodAlreadyOwnedException(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        User userGaby = randomUserWithName("Gaby");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = new Guest(userCarlos);
        Guest guestGaby = new Guest(userGaby);

        listOfGuests.add(guestCarlos);
        listOfGuests.add(guestGaby);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        newCanasta.confirmUser(userCarlos);
        newCanasta.confirmUser(userGaby);

        //Exercise(When)
        newCanasta.ownAGood(userCarlos,beer);
        newCanasta.ownAGood(userGaby,beer);

    }

    @Test
    public void whenACanastaIsClosedAllThePendingGuestConvertsToCancelled(){
        //Setup(Given)
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        User userGaby = randomUserWithName("Gaby");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = new Guest(userCarlos);
        Guest guestGaby = new Guest(userGaby);

        listOfGuests.add(guestCarlos);
        listOfGuests.add(guestGaby);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        newCanasta.confirmUser(userGaby);

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
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
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
        User userThatCreateTheCanasta = randomUserWithName("Ivan");
        User userCarlos = randomUserWithName("Carlos");
        List<Guest> listOfGuests = new ArrayList<>();

        Guest guestCarlos = GuestBuilder.buildAGuest()
                .withUser(userCarlos)
                .build();

        listOfGuests.add(guestCarlos);

        listOfGoods.add(beer);

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
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
