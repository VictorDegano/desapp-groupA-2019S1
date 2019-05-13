package ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour;

import ar.edu.unq.desapp.grupoa.exception.GoodAlreadyLoaded;
import ar.edu.unq.desapp.grupoa.exception.UserNotARepresentative;
import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.Baquita;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.baquita.LoadedGood;

public class LoadGood {

    private LoadGood(){}

    public static LoadedGood loadRepresentativeGood(BaquitaRepresentatives baquita, Good good, Guest guest) {
        if (!baquita.isRepresentative(guest)) {
            throw new UserNotARepresentative();
        }
        return loadGood(baquita, good, guest);
    }

    public static LoadedGood loadGood(Baquita baquita, Good good, Guest guest) {
        if (baquita.eventIsClosed()) {
            throw new CloseEventException(baquita);
        }
        if (guest.isInvitationPending()) {
            throw new ConfirmAsistanceException(baquita,guest.getUser());
        }
        if (baquita.goodIsloaded(good)) {
            throw new GoodAlreadyLoaded();
        }
        LoadedGood loadedGood = new LoadedGood(guest, good);
        baquita.loadGood(loadedGood);
        return loadedGood;
    }
}
