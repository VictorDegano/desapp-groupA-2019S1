package ar.edu.unq.desapp.grupoa.exception;

public class UserDefaultException extends RuntimeException {

    public UserDefaultException(){
        super("User has Defaulted and can't take more loans");
    }
}
