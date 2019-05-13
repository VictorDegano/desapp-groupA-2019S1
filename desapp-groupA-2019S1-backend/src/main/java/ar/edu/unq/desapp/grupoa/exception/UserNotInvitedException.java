package ar.edu.unq.desapp.grupoa.exception;

public class UserNotInvitedException extends RuntimeException {
    public UserNotInvitedException(){
        super("User not invited to the event");
    };
}
