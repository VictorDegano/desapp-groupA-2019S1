package ar.edu.unq.desapp.grupoa.exception;

public class UserNotARepresentative extends RuntimeException {

    public UserNotARepresentative(){
        super("User is not a representative of the event");
    }
}