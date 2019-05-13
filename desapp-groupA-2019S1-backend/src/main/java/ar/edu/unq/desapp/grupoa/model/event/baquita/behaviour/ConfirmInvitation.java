package ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.Baquita;

public class ConfirmInvitation {

    public static void confirmInvitation(Guest guest, Baquita baquita) {
        if (!baquita.isInvited(guest)) {
            throw new ConfirmAsistanceException(baquita,guest.getUser());
        }
        if (baquita.eventIsClosed()) {
            throw new CloseEventException(baquita);
        }
        guest.confirmAsistance();
    }
}
