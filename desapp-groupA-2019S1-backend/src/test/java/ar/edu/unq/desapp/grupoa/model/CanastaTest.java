package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.exception.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CloseCanasta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUserWithName;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CanastaTest {

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

        Good beer = GoodBuilder.buildAGood()
                .withName("Beer")
                .withQuantityForPerson(2)
                .build();

        List<Good> listOfGoods = new ArrayList<>();
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

        List<Good> listOfGoods = new ArrayList<>();

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

        List<Good> listOfGoods = new ArrayList<>();

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

        List<Good> listOfGoods = new ArrayList<>();

        Canasta newCanasta = new Canasta("Canastita",userThatCreateTheCanasta, listOfGuests,listOfGoods);
        newCanasta.setState(new CloseCanasta());
        //Exercise(When)
        newCanasta.confirmUser(userCarlos);

    }

}
