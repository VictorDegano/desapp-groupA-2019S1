package ar.edu.unq.desapp.grupoa.model.event.fiesta;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmationLimitException;
import ar.edu.unq.desapp.grupoa.exception.event.InvalidTemplateException;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.*;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class FiestaTest {

    @Test
    public void whenAGuestConfirmTheAssistance_TheQuantityOfConfirmationsChangeAndTheGuestHaveYourConfirmationInTrue(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();

        //Exercise(When)
        fiestaSUT.completeConfirmationAsistance(firstGuest);

        //Test(Then)
        assertEquals("Hubo mas de una confirmacion y solo se confirmo para un usuario",
                     Integer.valueOf(1),
                     fiestaSUT.getConfirmations());
        assertEquals("No se cambio la confirmacion del invitado al confirmar la asistencia",
                    InvitationState.ACCEPTED,
                    firstGuest.getConfirmAsistance());
    }

    @Test
    public void whenAGuestConfirmTheAsistance_TheQuantityOfGoodsAreRecalculated(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Good beer = GoodBuilder.buildAGood()
                               .withQuantityForPerson(2)
                               .build();

        Good doritoPack  = GoodBuilder.buildAGood()
                                      .withQuantityForPerson(1)
                                      .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGood(beer)
                                        .addGood(doritoPack)
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();
        //Exercise(When)
        fiestaSUT.completeConfirmationAsistance(firstGuest);

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
    public void ifAGuestThatAreNotInvitedTryToConfirmAssitance_getAExceptionAndNothingWasChange(){
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
            fiestaSUT.completeConfirmationAsistance(firstGuest);
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

    @Test(expected = CloseEventException.class)
    public void inAClosedFiestaIfTryToConfirmAsistance_GetAnExceptionOfCloseEvent(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta().withClosedState().build();

        //Exercise(When)
        fiestaSUT.confirmAsistancesOf(firstGuest);

        //Test(Then)
    }

    @Test(expected = ConfirmationLimitException.class)
    public void inAOpenFiestaIfTryToConfirmAsistance_GetAnExceptionOfConfirmationLimit(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest().build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .withLimitConfirmationDateTime(LocalDateTime.now().minusDays(5))
                                        .withOpenState()
                                        .build();

        //Exercise(When)
        fiestaSUT.confirmAsistancesOf(firstGuest);

        //Test(Then)
    }

    @Test
    public void inAOpenFiestaIfTryToConfirmAsistanceOfAInvitedGuest_TheConfirmationOcurrs(){
        //Setup(Given)
        User userToAssist = UserBuilder.buildAUser().build();

        Guest firstGuest = GuestBuilder.buildAGuest()
                                       .withUser(userToAssist)
                                       .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(3))
                                        .withOpenState()
                                        .addGuest(firstGuest)
                                        .withConfirmations(0)
                                        .build();

        //Exercise(When)
        fiestaSUT.confirmAsistancesOf(firstGuest);

        //Test(Then)
        assertEquals(Integer.valueOf(1), fiestaSUT.getConfirmations());
        assertEquals(InvitationState.ACCEPTED, firstGuest.getConfirmAsistance());
    }

    @Test
    public void whenAOpenStateIsClosed_ChangeToClosedStateAllPendingInvitationsAreCancelled(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest()
                                        .withConfirmation(InvitationState.ACCEPTED)
                                        .build();

        Guest secondGuest = GuestBuilder.buildAGuest()
                                        .withConfirmation(InvitationState.PENDING)
                                        .build();

        Guest thirtGuest = GuestBuilder.buildAGuest()
                                       .withConfirmation(InvitationState.CANCELLED)
                                       .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAFiesta()
                                        .addGuest(firstGuest)
                                        .addGuest(secondGuest)
                                        .addGuest(thirtGuest)
                                        .withOpenState()
                                        .build();
        //Exercise(When)
        fiestaSUT.close();

        //Test(Then)
        assertTrue("¡La fiesta no se cerro!", fiestaSUT.eventIsClosed());
        assertEquals("Se Cancelo la invitacion! Una invitacion aceptada no deberia cancelarce al cerrar una fiesta",
                     InvitationState.ACCEPTED,
                     getConfirmationAssistanceOfGuest(fiestaSUT, 0));
        assertEquals("No se cancelo la invitacion del usuario pendiente a cancelada",
                     InvitationState.CANCELLED,
                     getConfirmationAssistanceOfGuest(fiestaSUT, 1));
        assertEquals("Se cambio el estado de la invitacion del usuario y no debia suceder",
                     InvitationState.CANCELLED,
                     getConfirmationAssistanceOfGuest(fiestaSUT, 2));
    }

    @Test
    public void whenCreateAFiestaIfCreateWithATemplate_TheFiestaIsCreatedWithTheirGoods(){
        //Setup(Given)
        User organizer = UserBuilder.buildAUser().build();

        LocalDateTime limitTime = LocalDateTime.now();

        Good asado = GoodBuilder.buildAGood().withName("Asado").build();

        Good laDelDiego = GoodBuilder.buildAGood().withName("La del Diego").build();

        Template fiestaLocaTemplate = TemplateBuilder.buildATemplate()
                                                     .withEventType(EventType.FIESTA)
                                                     .addGood(asado)
                                                     .addGood(laDelDiego)
                                                     .build();

        //Exercise(Exercise)
        Fiesta fiestaSUT = Fiesta.createWithATemplate("La Fiesta Loca", organizer, new ArrayList<>(), limitTime, fiestaLocaTemplate);

        //Test(Test)
        assertEquals("No se cargo el nombre cuando se creo la fiesta con el template",
                     "La Fiesta Loca",
                     fiestaSUT.getName());

        assertEquals("El organizado no se asigno cuando se creo la fiesta",
                     organizer,
                     fiestaSUT.getOrganizer());

        assertTrue("Se cargaron invitados y la fiesta se creo sin invitados!",
                    fiestaSUT.getGuest().isEmpty());

        assertEquals("Se cambio la fecha limite al crear la fiesta!",
                     limitTime,
                     fiestaSUT.getLimitConfirmationDateTime());

        assertEquals("No es el asado! No es la Good del template",
                     asado.getName(),
                     fiestaSUT.getGoodsForGuest().get(0).getName());
        assertEquals("No es 'La Del Diego'! No es la Good del template",
                     laDelDiego.getName(),
                     fiestaSUT.getGoodsForGuest().get(1).getName());
    }

    @Test(expected = InvalidTemplateException.class)
    public void ifTryCreateAFiestaWithATemplateAndTheTemplateNotIsForFiesta_GetAnException(){
        //Setup(Given)
        Template canastaVaciaTemplate = TemplateBuilder.buildATemplate()
                .withEventType(EventType.CANASTA)
                .build();

        //Exercise(Exercise)
        Fiesta.createWithATemplate("", null, new ArrayList<>(), null, canastaVaciaTemplate);

        //Test(Test)
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]-------------[AUXILIARY METHODS]-------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    private InvitationState getConfirmationAssistanceOfGuest(Fiesta aFiesta, Integer position){
        return aFiesta.getGuest().get(position).getConfirmAsistance();
    }
}