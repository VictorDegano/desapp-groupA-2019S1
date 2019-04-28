package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import org.junit.Test;
import java.util.List;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUserWithName;
import static org.junit.Assert.*;

public class FiestaTest {

    @Test
    public void whenAGuestConfirmTheAssistance_TheQuantityOfConfirmationsChangeAndTheGuestHaveYourConfirmationInTrue(){
        //Setup(Given)
        User userToAssist = randomUserWithName("Ivan");

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withUser(userToAssist)
                                       .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();

        //Exercise(When)
        fiestaSUT.confirmAsistanceOf(firstGuest);

        //Test(Then)
        assertEquals("Hubo mas de una confirmacion y solo se confirmo para un usuario",
                     Integer.valueOf(1),
                     fiestaSUT.getConfirmations());
        assertEquals("No se cambio la confirmacion del invitado al confirmar la asistencia",
                     true,
                     firstGuest.getConfirmAsistance());
    }

    @Test
    public void whenAGuestConfirmTheAsistance_TheQuantityOfGoodsAreRecalculated(){
        //Setup(Given)
        User userToAssist = randomUserWithName("Ivan");

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withUser(userToAssist)
                                       .build();

        Good beer = GoodBuilder.buildAGood()
                               .withName("Beer")
                               .withQuantityForPerson(2)
                               .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withName("Dorito")
                                      .withQuantityForPerson(1)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGood(beer)
                                        .addGood(doritoPack)
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();
        //Exercise(When)
        fiestaSUT.confirmAsistanceOf(firstGuest);

        //Test(Then)
        List<Good> goodsAfterConfirm = fiestaSUT.getGoodsForGuest();

        assertEquals("No se calculo la cantidad final del Good al confirmar la asistencia",
                      Integer.valueOf(2),
                      goodsAfterConfirm.get(0).getFinalQuantity());
        assertEquals("No se calculo la cantidad final del Good al confirmar la asistencia",
                      Integer.valueOf(1),
                      goodsAfterConfirm.get(1).getFinalQuantity());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void ifAGuestThatAreNotInvitedTryToConfirmAssitance_getAExceptionAndNothingWhasChange(){
        //Setup(Given)
        User userToAssist = randomUserWithName("Ivan");

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withUser(userToAssist)
                                       .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withQuantityForPerson(1)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .withName("Pepelloza")
                                        .addGood(doritoPack)
                                        .withConfirmations(0)
                                        .build();

        String message = "";

        //Exercise(When)
        fiestaSUT.confirmAsistanceOf(firstGuest);
    }

}