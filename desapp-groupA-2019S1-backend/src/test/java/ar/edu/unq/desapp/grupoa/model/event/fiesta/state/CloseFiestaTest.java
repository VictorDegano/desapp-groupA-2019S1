package ar.edu.unq.desapp.grupoa.model.event.fiesta.state;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.model.event.CloseFiesta;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloseFiestaTest {

    @Test(expected = CloseEventException.class)
    public void whenTryToConfirmAClosedFiesta_GetAnException(){
        //Setup(Given)
        Guest guest = GuestBuilder.buildAGuest().build();
        Fiesta fiesta = FiestaBuilder.buildAFiesta().build();

        CloseFiesta closeStateSUT = new CloseFiesta(fiesta);
        //Exercise(When)
        closeStateSUT.confirmAssistanceOf(guest);

        //Test(Then)
    }

    @Test
    public void whenAClosedStateAskIfIsClosed_ReturnTrue() {
        //Setup(Given)
        Fiesta fiesta = FiestaBuilder.buildAFiesta().build();

        CloseFiesta closeStateSUT = new CloseFiesta(fiesta);
        //Exercise(When)
        Boolean isClosed = closeStateSUT.isClosed();

        //Test(Then)
        assertTrue(isClosed);
    }

    @Test
    public void whenDoNextState_getTheSameState() {
        //Setup(Given)
        Fiesta fiesta = FiestaBuilder.buildAFiesta().build();

        CloseFiesta closeStateSUT = new CloseFiesta(fiesta);
        //Exercise(When)

        //Test(Then)
        assertEquals(closeStateSUT, closeStateSUT.nextState());
    }
}