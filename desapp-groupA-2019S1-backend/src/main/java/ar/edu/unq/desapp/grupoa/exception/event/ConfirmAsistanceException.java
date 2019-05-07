package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class ConfirmAsistanceException extends RuntimeException {

    public ConfirmAsistanceException(Event event, User guest){
        super(String.format("Error confirming the invitation. The Event: %s has not as guest to %s",
                event.getName(),
                guest.fullName()));
    }
}
