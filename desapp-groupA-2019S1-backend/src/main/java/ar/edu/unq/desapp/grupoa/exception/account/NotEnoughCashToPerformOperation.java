package ar.edu.unq.desapp.grupoa.exception.account;

public class NotEnoughCashToPerformOperation extends RuntimeException {

    public NotEnoughCashToPerformOperation(String message){
        super(message);
    }
}
