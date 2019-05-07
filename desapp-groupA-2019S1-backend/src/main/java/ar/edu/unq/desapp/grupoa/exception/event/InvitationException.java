package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class InvitationException extends RuntimeException {

    public InvitationException(Event aEvent, User aUser){
        super(String.format("Error when try to invite at user %s. The Event: %s already has the user as a guest",
                aUser.fullName(),
                aEvent.getName()));
    }
}
