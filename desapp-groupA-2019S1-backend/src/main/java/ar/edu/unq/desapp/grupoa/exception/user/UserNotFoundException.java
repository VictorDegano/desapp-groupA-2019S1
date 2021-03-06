package ar.edu.unq.desapp.grupoa.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer userId){
        super(String.format("User with the id: %s not found",userId.toString()));
    }

}
