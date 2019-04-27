package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.utils.builders.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.GuestBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.desapp.grupoa.model.utils.RandomUserGenerator.randomUserWithName;
import static junit.framework.TestCase.assertEquals;

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




}
