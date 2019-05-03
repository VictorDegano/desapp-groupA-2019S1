package ar.edu.unq.desapp.grupoa.utils;

import java.util.Arrays;
import java.util.function.Function;

public class ComposeFunctions {

    private ComposeFunctions(){}

    //Composes all the functions given
    @SafeVarargs
    public static <T> Function<T, T> compose(Function<T, T>... funcs) {
        return Arrays.stream(funcs).reduce(Function.identity(), Function::andThen);
    }
}
