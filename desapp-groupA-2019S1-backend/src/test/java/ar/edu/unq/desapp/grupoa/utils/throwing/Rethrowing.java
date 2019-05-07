package ar.edu.unq.desapp.grupoa.utils.throwing;

// http://www.baeldung.com/java-sneaky-throws


public class Rethrowing {

    private Rethrowing(){}

    public static void rethrow(final ThrowingRunnable r){r.run();}
    public static <T> T rethrow(final ThrowingSupplier<T> supplier){return supplier.get();}
}
