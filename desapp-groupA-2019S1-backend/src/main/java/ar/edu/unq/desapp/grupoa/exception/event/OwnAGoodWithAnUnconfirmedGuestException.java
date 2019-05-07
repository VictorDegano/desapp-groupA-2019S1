package ar.edu.unq.desapp.grupoa.exception.event;

public class OwnAGoodWithAnUnconfirmedGuestException extends RuntimeException {

    public OwnAGoodWithAnUnconfirmedGuestException(String canastaName, String guestName){
        super(String.format("Error when try to take the cost. The Event: %s has not as guest to %s.",
                canastaName,
                guestName));
    }
}
