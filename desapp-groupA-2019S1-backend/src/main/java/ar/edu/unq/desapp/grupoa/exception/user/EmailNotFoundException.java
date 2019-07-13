package ar.edu.unq.desapp.grupoa.exception.user;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super ("Email not found: "+email);
    }
}
