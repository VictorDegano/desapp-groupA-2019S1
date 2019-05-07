package ar.edu.unq.desapp.grupoa.exception.event;

public class GoodAlreadyOwnedException extends RuntimeException {

    public GoodAlreadyOwnedException(String canastaName, String guestName){
        super(String.format("Error when try to take a cost of the canasta: %s. The Guest %s cannot take a cost that has been posseeeessed",
                            canastaName,
                            guestName));
    }

}

