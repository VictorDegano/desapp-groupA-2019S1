package ar.edu.unq.desapp.grupoa.model.utils;

import java.util.stream.IntStream;

public class StreamUtils {

    private StreamUtils() {
    }

    public static void repeat( Integer times,Runnable runnable){
        IntStream.rangeClosed(1, times).forEach( n ->runnable.run());
    }
}
