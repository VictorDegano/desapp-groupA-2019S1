package ar.edu.unq.desapp.grupoa.model.stateFiesta;

import ar.edu.unq.desapp.grupoa.exception.ConfirmationLimitException;
import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;
import ar.edu.unq.desapp.grupoa.model.InvitationState;
import ar.edu.unq.desapp.grupoa.utils.builders.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.GuestBuilder;
import org.aspectj.apache.bcel.classfile.Module;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class OpenFiestaTest {

    @Test(expected = ConfirmationLimitException.class )
    public void whenConrfirmAGuestInvitedToTheFiestaAndTheDateLimitIsReached_ThrowAExceptions() {
        //Setup(Given)
        Fiesta fiestaToConfirm = FiestaBuilder.buildAFiesta()
                                              .withLimitConfirmationDateTime(LocalDateTime.now().minusDays(2))
                                              .build();

        Guest guestToConfirm = GuestBuilder.buildAGuest().build();

        OpenFiesta openFiestaSUT = new OpenFiesta(fiestaToConfirm);

        //Exercise(When)
        openFiestaSUT.confirmAssistanceOf(guestToConfirm);

        //Test(Then)
    }

    @Test
    public void whenConfirmAGuestInvitedToTheFiestaAndTheDateLimitHasNotBeenReached_TheConfirmationHasDone(){
        //Setup(Given)
        Guest guestToConfirm = GuestBuilder.buildAGuest().build();

        Fiesta fiestaToConfirm = FiestaBuilder.buildAFiesta()
                                              .withConfirmations(0)
                                              .addGuest(guestToConfirm)
                                              .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
                                              .build();

        OpenFiesta openFiestaSUT = new OpenFiesta(fiestaToConfirm);

        //Exercise(When)
        openFiestaSUT.confirmAssistanceOf(guestToConfirm);

        //Test(Then)
        assertEquals("No Confirmo la asistencia, se deberia haber completado el proceso de confirmacion",
                     InvitationState.ACCEPTED,
                     guestToConfirm.getConfirmAsistance());
    }

    @Test
    public void whenAskIfIsClosed_ReturnFalse() {
        //Setup(Given)
        Fiesta fiesta = FiestaBuilder.buildAFiesta().build();

        OpenFiesta openStateSUT = new OpenFiesta(fiesta);
        //Exercise(When)
        Boolean isClosed = openStateSUT.isClosed();

        //Test(Then)
        assertFalse(isClosed);
    }

    @Test
    public void whenGetTheNextState_GetClosedState() {
        //Setup(Given)
        Fiesta fiesta = FiestaBuilder.buildAFiesta().build();

        OpenFiesta openStateSUT = new OpenFiesta(fiesta);
        //Exercise(When)
        FiestaState nextState = openStateSUT.nextState();

        //Test(Then)
        assertTrue(nextState.isClosed());
        assertNotEquals(openStateSUT.getClass(), nextState.getClass());
        assertEquals(CloseFiesta.class, nextState.getClass());
    }
}