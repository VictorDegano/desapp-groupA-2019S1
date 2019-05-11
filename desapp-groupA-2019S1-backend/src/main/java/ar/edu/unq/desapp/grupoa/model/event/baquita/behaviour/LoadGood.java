package ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour;

import ar.edu.unq.desapp.grupoa.exception.GoodAlreadyLoaded;
import ar.edu.unq.desapp.grupoa.exception.UserNotARepresentative;
import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.baquita.LoadedGood;

public class LoadGood {

    private LoadGood(){}

    public static LoadedGood loadGood(BaquitaRepresentatives baquita, Good good, Guest guest, Integer amount) {
        if (!baquita.isRepresentative(guest)) {
            throw new UserNotARepresentative();
        }
        if (baquita.eventIsClosed()) {
            throw new CloseEventException();
        }
        if (guest.isInvitationPending()) {
            throw new ConfirmAsistanceException(baquita.getName(), guest.getUser().getFirstName());
        }
        if (baquita.goodIsloaded(good)) {
            throw new GoodAlreadyLoaded();
        }
        LoadedGood loadedGood = new LoadedGood(guest, good, amount);
        baquita.loadGood(loadedGood);
        return loadedGood;
    }
}
