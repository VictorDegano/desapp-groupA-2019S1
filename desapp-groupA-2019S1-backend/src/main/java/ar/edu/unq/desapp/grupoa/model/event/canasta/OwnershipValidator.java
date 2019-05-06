package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.GoodAlreadyOwnedException;
import ar.edu.unq.desapp.grupoa.exception.event.OwnAGoodWithAnUnconfirmedGuestException;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class OwnershipValidator {

    public static void ownAGood(User user, CanastaGood good, Canasta canasta) {

        validateInvitation(user,canasta);
        validateAvailability(user,good,canasta);

        canasta.getGuestOfUser(user).ownAGood(good);

    }

    private static void validateAvailability(User user,CanastaGood good,Canasta canasta) {
        if(!good.isAvailable()){
            throw new GoodAlreadyOwnedException(canasta.getName(),user.getFirstName());
        }
    }

    private static void validateInvitation(User user,Canasta canasta){
        if(!canasta.getGuestOfUser(user).isInvitationAccepted()){
            throw new OwnAGoodWithAnUnconfirmedGuestException(canasta.getName(),user.getFirstName());
        }
    }
}
