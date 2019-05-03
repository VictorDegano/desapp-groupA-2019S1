package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.Baquita;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.function.Function;

import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomString;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;


public class BaquitaBuilder {


    private BaquitaBuilder() {
    }

    @SafeVarargs
    public static Baquita newRandomBaquita(Function<Baquita, Baquita>... functions) {
        return compose(functions).apply(new Baquita(randomString(), randomUser(), new ArrayList<>(), new ArrayList<>()));
    }

    public static Function<Baquita, Baquita> withGuest(Guest guest) {
        return (baquita) -> {
            baquita.addGuest(guest);
            return baquita;
        };
    }

}