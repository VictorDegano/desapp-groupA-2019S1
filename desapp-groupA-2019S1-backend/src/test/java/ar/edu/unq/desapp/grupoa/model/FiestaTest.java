package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.exception.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.utils.builders.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.GoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.GuestBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.UserBuilder;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class FiestaTest {

    @Test
    public void whenAGuestConfirmTheAssistance_TheQuantityOfConfirmationsChangeAndTheGuestHaveYourConfirmationInTrue(){
        //Setup(Given)
        User userToAssist = UserBuilder.buildAUser()
                                       .withFirstName("Ivan")
                                       .build();

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
        assertTrue("No se cambio la confirmacion del invitado al confirmar la asistencia",
                    firstGuest.getConfirmAsistance());
    }

    @Test
    public void whenAGuestConfirmTheAsistance_TheQuantityOfGoodsAreRecalculated(){
        //Setup(Given)
        User userToAssist = UserBuilder.buildAUser()
                                       .withFirstName("Ivan")
                                       .build();

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

    @Test
    public void ifAGuestThatAreNotInvitedTryToConfirmAssitance_getAExceptionAndNothingWhasChange(){
        //Setup(Given)
        User userToAssist =  UserBuilder.buildAUser()
                                        .withFirstName("Ivan")
                                        .withLastName("Dominikow")
                                        .build();

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withUser(userToAssist)
                                       .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withQuantityForPerson(1)
                                      .withFinalQuantity(1)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .withName("Pepelloza")
                                        .addGood(doritoPack)
                                        .withConfirmations(0)
                                        .build();

        String message = "";

        //Exercise(When)
        try{
            fiestaSUT.confirmAsistanceOf(firstGuest);
        } catch (ConfirmAsistanceException cae){
            message = cae.getMessage();
        }

        //Test(Then)
        assertEquals("No Hubo Excepcion! un invitado que no esta en la fiesta pudo confirmar asistencia",
                     "Error al confirmar la invitacion. La Fiesta: Pepelloza no tiene como invitado a Ivan Dominikow",
                     message);
        assertEquals("Ocurrio la Excepcion y se cambio la cantidad de confirmaciones! ¡Eso no tiene que ocurrir!",
                     Integer.valueOf(0),
                     fiestaSUT.getConfirmations());
        assertEquals("Ocurrio la Excepcion y se cambio la cantidad final de los Goods! ¡No tiene que pasar!",
                     Integer.valueOf(1),
                     fiestaSUT.getGoodsForGuest().get(0).getFinalQuantity());
    }

    @Test
    public void ifTheFiestaHave2ConfirmationsAndTwoAGoodsWithPriceThirtyEachOne_WhenSeeTotalCostTheCost_IsSixty(){
        //Setup(given)
        Good beerPack  = GoodBuilder.buildAGood()
                                    .withPricesPerUnit(20)
                                    .withQuantityForPerson(1)
                                    .withFinalQuantity(2)
                                    .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withPricesPerUnit(20)
                                      .withQuantityForPerson(2)
                                      .withFinalQuantity(4)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGood(doritoPack)
                                        .addGood(beerPack)
                                        .build();

        //Exercise(When)
        Integer totalCost = fiestaSUT.totalCost();

        //Test(Then)
        assertEquals("No se calculo correctamente el costo total de la fiesta, algun good no se sumo o calculo mal",
                    Integer.valueOf(200),
                    totalCost);
    }

}