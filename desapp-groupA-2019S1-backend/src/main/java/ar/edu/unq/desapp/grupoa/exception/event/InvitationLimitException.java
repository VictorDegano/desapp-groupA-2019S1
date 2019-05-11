package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.user.User;

public class InvitationLimitException extends RuntimeException {

    public InvitationLimitException(User userToInvite) {
        super(String.format("Cannot invite at the user %s", userToInvite.fullName()));
    }
}
