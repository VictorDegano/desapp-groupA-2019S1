package ar.edu.unq.desapp.grupoa.model.event.baquita;


import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.model.event.baquita.behaviour.ConfirmInvitation.confirmInvitation;

import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.newRandomBaquitaComunitary;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.newRandomBaquitaComunitaryWithOwner;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.withConfirmedGuest;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.withGood;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.withAccountBalance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaquitaComunitaryTest {

    @Test
    public void aBaquitaIsCreatedWithStateInPreparation() {
        Baquita baquita = newRandomBaquitaComunitary();

        assertFalse(baquita.eventIsClosed());
    }


    @Test
    public void aUserConfirmsHisAsistenceToTheBaquita() {
        Guest guest = new Guest(randomUser());
        BaquitaComunitary baquita = newRandomBaquitaComunitary();
        baquita.addGuest(guest);

        assertTrue(baquita.isInvited(guest));
        assertTrue(guest.isInvitationPending());

        confirmInvitation(guest, baquita);

        assertFalse(guest.isInvitationPending());
    }

    @Test(expected = ConfirmAsistanceException.class)
    public void aUserCantConfirmsHisAsistenceIfHeIsNotInvited() {
        Guest guest = new Guest(randomUser());
        BaquitaComunitary baquita = newRandomBaquitaComunitary();

        confirmInvitation(guest, baquita);
    }

    @Test(expected = CloseEventException.class)
    public void aUserCantConfirmsHisAsistenceIfTheBaquitaIsClosed() {
        Guest guest = new Guest(randomUser());
        BaquitaComunitary baquita = newRandomBaquitaComunitary();
        baquita.addGuest(guest);

        baquita.close();

        confirmInvitation(guest, baquita);
    }

    @Test
    public void aBaquitaIsClosedAndAllCurrentPendingInvitationsAreCanceled() {
        Guest guest = new Guest(randomUser());

        BaquitaComunitary baquita = newRandomBaquitaComunitary();
        baquita.addGuest(guest);

        baquita.close();

        assertTrue(guest.isCanceled());
    }

    @Test
    public void aBaquitaWithOwnerAndATwoGuestsConfirmedClosesAndThePriceIsDistributedToTheGuestsAndTheMoneyGoesToTheOwner() {
        User owner = randomUser();
        Guest guest1 = new Guest(randomUser(withAccountBalance(100)));
        Guest guest2 = new Guest(randomUser(withAccountBalance(100)));


        BaquitaComunitary baquita = newRandomBaquitaComunitaryWithOwner(owner,
                withConfirmedGuest(guest1), withConfirmedGuest(guest2),
                withGood(newGoodWithPrice(70)), withGood(newGoodWithPrice(30))
        );

        baquita.close();

        Integer priceToPayEach = baquita.totalCost() / 2;
        Integer expectedUserBalance = 100 - priceToPayEach;


        assertEquals(balance(owner), baquita.totalCost());
        assertEquals(balance(guest1.getUser()), expectedUserBalance);
        assertEquals(balance(guest2.getUser()), expectedUserBalance);
    }

    @Test
    public void priceIsntDistributedForUnconfirmedGuestWhenTheBaquitaCloses() {
        User owner = randomUser(withAccountBalance(0));
        Guest guest1 = new Guest(randomUser(withAccountBalance(100)));
        Guest guest2 = new Guest(randomUser(withAccountBalance(100)));


        BaquitaComunitary baquita = newRandomBaquitaComunitaryWithOwner(owner,
                withConfirmedGuest(guest1),
                withGood(newGoodWithPrice(70)), withGood(newGoodWithPrice(30))
        );

        baquita.close();

        assertEquals(balance(owner), baquita.totalCost());
        assertEquals(balance(guest1.getUser()), integer(0));
        assertEquals(balance(guest2.getUser()), integer(100));
    }


    private Integer balance(User user) {
        return user.getAccount().balance();
    }

    private Good newGoodWithPrice(Integer price) {
        Good good = new Good();
        good.setPricePerUnit(price);
        good.setQuantityForPerson(1);
        return good;
    }

}
