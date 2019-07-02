package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.exception.GoodAlreadyLoaded;
import ar.edu.unq.desapp.grupoa.exception.UserNotARepresentative;
import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.ConfirmInvitation.confirmInvitation;
import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.LoadGood.loadGood;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.newBaquitaRepresentativesWithOwner;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.newRandomBaquitaRepresentatives;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withConfirmedGuestForBaquitaRepresentatives;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withConfirmedRepresentative;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withLoadedGoodFrom;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withRepresentative;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.withAccountBalance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaquitaRepresentativesTest {


    @Test
    public void aBaquitaIsCreatedWithStateInPreparation() {
        Baquita baquita = newRandomBaquitaRepresentatives();

        assertFalse(baquita.eventIsClosed());
    }

    @Test
    public void aBaquitaIsCreatedWithOneRepresentative() {
        Guest guest = new Guest(randomUser());
        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addRepresentative(guest);

        assertTrue(baquita.isRepresentative(guest));
    }

    @Test
    public void aUserConfirmsHisAsistenceToTheBaquita() {
        Guest guest = new Guest(randomUser());
        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addGuest(guest);

        assertTrue(baquita.isInvited(guest));
        assertTrue(guest.isInvitationPending());

        confirmInvitation(guest, baquita);

        assertFalse(guest.isInvitationPending());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void aUserCantConfirmsHisAsistenceIfHeIsNotInvited() {
        Guest guest = new Guest(randomUser());
        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();

        confirmInvitation(guest, baquita);
    }

    @Test(expected = CloseEventException.class)
    public void aUserCantConfirmsHisAsistenceIfTheBaquitaIsClosed() {
        Guest guest = new Guest(randomUser());
        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addGuest(guest);

        baquita.close();

        confirmInvitation(guest, baquita);
    }

    @Test
    public void aRepresentativeLoadsAGoodOfTheEventFor100Pesos() {
        Guest guest = new Guest(randomUser());
        Good good = newGoodWithPrice(100);

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addRepresentative(guest);
        confirmInvitation(guest, baquita);
        baquita.addGood(good);

        loadGood(baquita, good, guest);
        assertTrue(baquita.goodIsloaded(good));
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void aRepresentativeCantLoadAGoodIfHeHasntConfirmed() {
        Guest guest = new Guest(randomUser());
        Good good = newGoodWithPrice(100);

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addGood(good);
        baquita.addRepresentative(guest);

        loadGood(baquita, good, guest);
    }


    @Test(expected = UserNotARepresentative.class)
    public void cantLoadAGoodIfRepresentativeIsntInvited() {
        Guest guest = new Guest(randomUser());
        Good good = newGoodWithPrice(100);

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addGood(good);

        loadGood(baquita, good, guest);
    }

    @Test(expected = CloseEventException.class)
    public void aRepresentativeCantLoadAGoodIfTheBaquitaIsClose() {
        Guest guest = new Guest(randomUser(withAccountBalance(100)));
        Good good = newGoodWithPrice(100);

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addRepresentative(guest);
        confirmInvitation(guest, baquita);
        baquita.addGood(good);
        baquita.close();

        loadGood(baquita, good, guest);
    }


    @Test(expected = UserNotARepresentative.class)
    public void aNormalGuestCantLoadAGood() {
        Guest guest = new Guest(randomUser());
        Good good = newGoodWithPrice(100);

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addGood(good);
        baquita.addGuest(guest);
        confirmInvitation(guest, baquita);

        loadGood(baquita, good, guest);
    }

    @Test(expected = GoodAlreadyLoaded.class)
    public void aRepresentativeCantLoadAGoodIfItWasAlreadyLoaded() {
        Guest guest = new Guest(randomUser());
        Good good = newGoodWithPrice(randomNumber());

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addRepresentative(guest);
        confirmInvitation(guest, baquita);
        baquita.addGood(good);

        loadGood(baquita, good, guest);
        loadGood(baquita, good, guest);
    }

    @Test
    public void aBaquitaIsClosedAndAllCurrentPendingInvitationsAndPendingRepresentativesAreCanceled() {
        Guest guest = new Guest(randomUser());
        Guest representative = new Guest(randomUser());

        BaquitaRepresentatives baquita = newRandomBaquitaRepresentatives();
        baquita.addRepresentative(representative);
        baquita.addGuest(guest);

        baquita.close();

        assertTrue(guest.isCanceled());
        assertTrue(representative.isCanceled());
    }

    @Test
    public void aBaquitaWithOwnerARepresentativeAGuestsConfirmedClosesAndThePricesAreDistributedEvenly() {
        User owner = randomUser(withAccountBalance(100));
        Guest guest1 = new Guest(randomUser(withAccountBalance(100)));
        Guest representative1 = new Guest(randomUser(withAccountBalance(100)));

        BaquitaRepresentatives baquita = newBaquitaRepresentativesWithOwner(owner,
                withConfirmedRepresentative(representative1), withConfirmedGuestForBaquitaRepresentatives(guest1),
                withLoadedGoodFrom(representative1, 70), withLoadedGoodFrom(representative1, 30)
        );

        Integer priceToPayEach = baquita.totalCost() / 3;
        Integer expectedUserBalance = 100 - priceToPayEach;

        baquita.close();

        assertEquals(balance(owner), expectedUserBalance);
        assertEquals(balance(guest1.getUser()), expectedUserBalance);
        assertEquals(balance(representative1.getUser()), expectedUserBalance);
    }

    private Integer balance(User user) {
        return user.getAccount().balance();
    }


    @Test
    public void priceIsntDistributedForUnconfirmedGuestOrRepresentativeWhenTheBaquitaCloses() {
        User owner = randomUser(withAccountBalance(100));
        Guest representative1 = new Guest(randomUser(withAccountBalance(100)));
        Guest representative2 = new Guest(randomUser(withAccountBalance(100)));

        BaquitaRepresentatives baquita = newBaquitaRepresentativesWithOwner(owner,
                withConfirmedRepresentative(representative1), withRepresentative(representative2),
                withLoadedGoodFrom(representative1, 70), withLoadedGoodFrom(representative1, 30)
        );

        Integer priceToPayEach      = baquita.totalCost() / 2;
        Integer expectedUserBalance = 100 - priceToPayEach;

        baquita.close();

        assertEquals(balance(owner), expectedUserBalance);
        assertEquals(balance(representative1.getUser()), expectedUserBalance);
        assertEquals(balance(representative2.getUser()), integer(100));
    }

    private Good newGoodWithPrice(Integer price) {
        Good good = new Good();
        good.setPricePerUnit(price);
        good.setQuantityForPerson(1);
        return good;
    }

}
