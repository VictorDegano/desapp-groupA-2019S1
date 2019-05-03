package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaBuilder.newRandomBaquita;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaBuilder.withGuest;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaquitaRepresentantesTest {

    @Before
    public void setup(){
    }

    @Test
    public void aBaquitaIsCreatedWithStateInPreparation(){
        Baquita baquita = newRandomBaquita();

        assertFalse(baquita.eventIsClosed());
    }

    //Dejar comportamiento solo cuando estoy hablando de mi estructura. Si no estoy hablando sobre mi mismo, sacar afuera

    @Test
    public void aUserConfirmsHisAsistenceToTheBaquita(){
        Guest    guest  = new Guest(randomUser());
        Baquita baquita = newRandomBaquita(withGuest(guest));

        assertTrue(isInvited(guest,baquita));
        assertTrue(guest.isInvitationPending());

        confirmInvitation(guest,baquita);

        assertFalse(guest.isInvitationPending());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void aUserCantConfirmsHisAsistenceIfHeIsNotInvited(){
        Guest    guest  = new Guest(randomUser());
        Baquita baquita = newRandomBaquita();

        confirmInvitation(guest,baquita);

        assertTrue(guest.isInvitationPending());
    }

    @Test(expected = CloseEventException.class)
    public void aUserCantConfirmsHisAsistenceIfTheBaquitaIsClosed(){
        Guest    guest  = new Guest(randomUser());
        Baquita baquita = newRandomBaquita(withGuest(guest));

        baquita.close();

        confirmInvitation(guest,baquita);

        assertTrue(guest.isInvitationPending());
    }


    private static Boolean isInvited(Guest guest, Baquita baquita) {
        List<Guest> guests = baquita.getGuests();
        return guests.stream().anyMatch(baquitaGuest-> baquitaGuest.equals(guest));
    }

    private static void confirmInvitation(Guest guest, Baquita baquita) {
        if (!isInvited(guest,baquita)){
            throw new ConfirmAsistanceException(baquita.getName(),guest.getUser().getFirstName());
        }
        if(baquita.eventIsClosed()){
            throw new CloseEventException();
        }

        guest.confirmAsistance();
    }
}
