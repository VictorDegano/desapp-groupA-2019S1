package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.function.Function;

import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.ConfirmInvitation.confirmInvitation;

import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomString;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;


//TODO: Refactor entre BaquitacomunitaryBuilder y BaquitaRepresentativeBuilder
public class BaquitaComunitaryBuilder {

    private BaquitaComunitaryBuilder() {
    }

    @SafeVarargs
    public static BaquitaComunitary newRandomBaquitaComunitary(Function<BaquitaComunitary, BaquitaComunitary>... functions) {
        return  compose(functions).apply(new BaquitaComunitary(randomString(), randomUser(), new ArrayList<>(), new ArrayList<>()));
    }

    @SafeVarargs
    public static BaquitaComunitary newRandomBaquitaComunitaryWithOwner(User owner, Function<BaquitaComunitary, BaquitaComunitary>... functions) {
        return compose(functions).apply(new BaquitaComunitary(randomString(), owner, new ArrayList<>(), new ArrayList<>()));
    }

    public static Function<BaquitaComunitary, BaquitaComunitary> withConfirmedGuest(Guest guest) {
        return (baquita) -> {
            baquita.addGuest(guest);
            confirmInvitation(guest,baquita);
            return baquita;
        };
    }

    public static Function<BaquitaComunitary, BaquitaComunitary> withGood(Good good) {
        return (baquita) -> {
            baquita.addGood(good);
            return baquita;
        };
    }

}
