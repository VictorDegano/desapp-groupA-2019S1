package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.testUtils.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.testUtils.GoodBuilder;
import ar.edu.unq.desapp.grupoa.testUtils.GuestBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FiestaTest {

    @Test
    public void whenAGuestConfirmTheAssistance_TheQuantityOfConfirmationsChange(){
        //Setup(Given)
        User userToAssist = new User("Ivan");

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withName(userToAssist)
                                       .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAGuest()
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();

        //Exercise(When)
        fiestaSUT.confirmAsistanceOf(userToAssist);

        //Test(Then)

        assertEquals("Hubo mas de una confirmacion y solo se confirmo para un usuario",
                     Integer.valueOf(1),
                     fiestaSUT.getConfirmations());
    }

    @Test
    public void whenAGuestConfirmTheAsistance_TheQuantityOfGoodsAreRecalculated(){
        //Setup(Given)
        User userToAssist = new User("Ivan");

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withName(userToAssist)
                                       .build();

        Good beer = GoodBuilder.buildAGood()
                               .withName("Beer")
                               .withQuantityForPerson(2)
                               .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withName("Dorito")
                                      .withQuantityForPerson(1)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAGuest()
                                        .addGood(beer)
                                        .addGood(doritoPack)
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();
        //Exercise(When)
        fiestaSUT.confirmAsistanceOf(userToAssist);

        //Test(Then)
        List<Good> goodsAfterConfirm = fiestaSUT.getGoodsForGuest();

        assertEquals("No se calculo la cantidad final del Good al confirmar la asistencia",
                      Integer.valueOf(2),
                      goodsAfterConfirm.get(0).getFinalQuantity());
        assertEquals("No se calculo la cantidad final del Good al confirmar la asistencia",
                      Integer.valueOf(1),
                      goodsAfterConfirm.get(1).getFinalQuantity());
    }

}