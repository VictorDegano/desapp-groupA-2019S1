package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.function.Function;


import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.ConfirmInvitation.confirmInvitation;
import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.LoadGood.loadGood;
import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;
import static ar.edu.unq.desapp.grupoa.utils.builder.GoodBuilder.buildAGood;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomString;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;


public class BaquitaBuilder {


    private BaquitaBuilder() {
    }

    @SafeVarargs
    public static BaquitaRepresentatives newRandomBaquitaRepresentatives(Function<BaquitaRepresentatives, BaquitaRepresentatives>... functions) {
        return compose(functions).apply(new BaquitaRepresentatives(randomString(), randomUser(), new ArrayList<>(), new ArrayList<>()));
    }

    @SafeVarargs
    public static BaquitaRepresentatives newBaquitaRepresentativesWithOwner(User owner, Function<BaquitaRepresentatives, BaquitaRepresentatives>... functions) {
        return compose(functions).apply(new BaquitaRepresentatives(randomString(), owner, new ArrayList<>(), new ArrayList<>()));
    }


    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withRepresentative(Guest representative) {
        return (baquita) -> {
            baquita.addRepresentative(representative);
            return baquita;
        };
    }

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withConfirmedRepresentative(Guest representative) {
        return (baquita) -> {
            baquita.addRepresentative(representative);
            confirmInvitation(representative,baquita);
            return baquita;
        };
    }

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withConfirmedGuest(Guest representative) {
        return (baquita) -> {
            baquita.addGuest(representative);
            confirmInvitation(representative,baquita);
            return baquita;
        };
    }

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withLoadedGoodFrom(Guest representative, Integer amount) {
        return (baquita) -> {
            Good good = buildAGood().build();
            baquita.addGood(good);
            loadGood(baquita,good,representative,amount);
            return baquita;
        };
    }


}