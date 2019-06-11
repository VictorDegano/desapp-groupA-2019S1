package ar.edu.unq.desapp.grupoa.exception;

public class GoodAlreadyLoaded extends RuntimeException {
    public GoodAlreadyLoaded(){
        super ("The good was already loaded by other user");
    }
}
