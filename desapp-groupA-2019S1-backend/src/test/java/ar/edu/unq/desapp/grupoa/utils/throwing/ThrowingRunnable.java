package ar.edu.unq.desapp.grupoa.utils.throwing;

public interface ThrowingRunnable extends Runnable {

    @Override
    default void run(){
        try{
            throwingRun();
        } catch (final Throwable ex){
            throw new RuntimeException(ex);
        }
    }

    void throwingRun() throws Throwable;
}
