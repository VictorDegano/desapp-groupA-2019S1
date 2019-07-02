package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Function;


import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.ConfirmInvitation.confirmInvitation;
import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.LoadGood.loadGood;
import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomString;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;


//TODO: Refactor entre BaquitacomunitaryBuilder y BaquitaRepresentativeBuilder
public class BaquitaRepresentativesBuilder {


    private BaquitaRepresentativesBuilder() {
    }

    @SafeVarargs
    public static BaquitaRepresentatives newRandomBaquitaRepresentatives(Function<BaquitaRepresentatives, BaquitaRepresentatives>... functions) {
        return  compose(functions).apply(new BaquitaRepresentatives(randomString(), randomUser(), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now()));
    }

    @SafeVarargs
    public static BaquitaRepresentatives newBaquitaRepresentativesWithOwner(User owner, Function<BaquitaRepresentatives, BaquitaRepresentatives>... functions) {
        return compose(functions).apply(new BaquitaRepresentatives(randomString(), owner, new ArrayList<>(), new ArrayList<>(), LocalDateTime.now()));
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

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withConfirmedGuestForBaquitaRepresentatives(Guest representative) {
        return (baquita) -> {
            baquita.addGuest(representative);
            confirmInvitation(representative,baquita);
            return baquita;
        };
    }

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withUnconfirmedGuestForBaquitaRepresentatives(Guest representative) {
        return (baquita) -> {
            baquita.addGuest(representative);
            return baquita;
        };
    }

    public static Function<BaquitaRepresentatives, BaquitaRepresentatives> withLoadedGoodFrom(Guest representative, Integer amount) {
        return (baquita) -> {
            Good good = new Good();
            good.setPricePerUnit(amount);
            good.setQuantityForPerson(1);
            good.setName("Beer");
            baquita.addGood(good);
            loadGood(baquita,good,representative);
            return baquita;
        };
    }


}