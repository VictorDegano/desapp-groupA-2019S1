package ar.edu.unq.desapp.grupoa.utils.throwing;

import java.util.function.Supplier;

public interface ThrowingSupplier<T> extends Supplier<T> {
    @Override
    default T get(){
        try{
            return throwingGet();
        } catch (final Throwable ex){
            throw new RuntimeException(ex);
        }
    }

    T throwingGet() throws Throwable;
}
