package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;

public abstract class Baquita extends Event {

    protected BaquitaState state;
    protected List<LoadedGood> loadedGoods;

    public Baquita(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest){
        this.loadedGoods = new ArrayList<>();
        this.name = name;
        this.organizer = organizer;
        this.guests = guests;
        this.goodsForGuest = goodsForGuest;
        this.state = BaquitaState.OPEN;
    }

    @Override
    public boolean eventIsClosed() {
        return this.state.equals(BaquitaState.CLOSE);
    }

    @Override
    public void close() {
        this.state = BaquitaState.CLOSE;
        cancelPendingGuests();
        distributePayment();
    }

    protected List<Guest> confirmed(List<Guest> guests) {
        return guests.stream().filter(Guest::isconfirmed).collect(Collectors.toList());
    }


    protected abstract void distributePayment();

    protected List<Guest> getConfirmedGuests() {
        List<Guest> guests = confirmed(this.guests);
        return new ArrayList<>(guests);
    }

    //TODO: Esto deberia ser un behaviour, cuando toque crear los services lo paso alla.
    protected void pay(Integer priceToPay, User user) {
        Account account = extract(user.getAccount(), priceToPay);
        user.updateAccount(account);
    }

    public Integer totalCost() {
        return loadedGoods.stream().mapToInt(LoadedGood::getAmount).sum();
    }

    @Override
    public void confirmAsistancesOf(Guest guestToAssist) {
        guestToAssist.confirmAsistance();
    }

    public void cancelPendingGuests() {
        cancelGuests(this.guests);
    }

    protected void cancelGuests(List<Guest> guests) {
        guests.forEach(this::cancelIfPending);
    }

    protected void cancelIfPending(Guest guest) {
        if (guest.isInvitationPending()) {
            guest.cancelInvitation();
        }
    }

    public void loadGood(LoadedGood loadedGood){
        this.loadedGoods.add(loadedGood);
    }

    public Boolean goodIsloaded(Good good) {
        return this.loadedGoods.stream().anyMatch(loadedGood -> loadedGood.getGood().equals(good));
    }

    public Boolean isInvited(Guest guest) {
        return guests.stream().anyMatch(baquitaGuest -> baquitaGuest.equals(guest));
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void addGood(Good good) {
        goodsForGuest.add(good);
    }
}
