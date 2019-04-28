package ar.edu.unq.desapp.grupoa.model.stateFiesta;

import ar.edu.unq.desapp.grupoa.exception.CloseEventException;
import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;
import ar.edu.unq.desapp.grupoa.utils.builders.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builders.GuestBuilder;
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