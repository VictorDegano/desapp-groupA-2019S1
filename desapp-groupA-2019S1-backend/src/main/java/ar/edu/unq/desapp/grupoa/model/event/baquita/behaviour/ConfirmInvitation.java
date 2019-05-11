package ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;

public class ConfirmInvitation {

    public static void confirmInvitation(Guest guest, BaquitaRepresentatives baquita) {
        if (!baquita.isInvited(guest) && !baquita.isRepresentative(guest)) {
            throw new ConfirmAsistanceException(baquita.getName(), guest.getUser().getFirstName());
        }
        if (baquita.eventIsClosed()) {
            throw new CloseEventException();
        }

        guest.confirmAsistance();
    }
}
