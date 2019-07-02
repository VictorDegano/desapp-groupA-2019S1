package ar.edu.unq.desapp.grupoa.exception;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(Integer guestId) {
        super(String.format("The Guest with the id: %s not found", guestId.toString()));
    }
}
