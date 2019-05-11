package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;

public class BaquitaRepresentatives extends Baquita {


    private List<Guest> representatives;
    private List<LoadedGood> loadedGoods;

    public BaquitaRepresentatives(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest) {
        super(name, organizer, guests, goodsForGuest);
        this.representatives = new ArrayList<>();
        this.loadedGoods = new ArrayList<>();
    }

    @Override
    public void close() {
        this.state = BaquitaState.CLOSE;
            cancelPendingGuests();
            cancelPendingRepresentatives();
            distributePayment();
    }

    private List<Guest> confirmed(List<Guest> guests) {
        return guests.stream().filter(Guest::isconfirmed).collect(Collectors.toList());
    }


    private void distributePayment() {
        List<Guest> guests = getConfirmedGuests();
        Integer priceToPay = totalCost() / (guests.size()+1);
        guests.forEach(guest -> pay(priceToPay, guest.getUser()));
        pay(priceToPay,organizer);
    }

    private List<Guest> getConfirmedGuests() {
        List<Guest> representatives = confirmed(this.representatives);
        List<Guest> guests = confirmed(this.guests);
        List<Guest> confirmedGuests = new ArrayList<>(guests);
        confirmedGuests.addAll(representatives);
        return confirmedGuests;
    }


    private void pay(Integer priceToPay, User user) {
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

    public void cancelPendingRepresentatives() {
        cancelGuests(this.representatives);
    }

    public void cancelPendingGuests() {
        cancelGuests(this.guests);
    }

    public void cancelGuests(List<Guest> guests) {
        guests.forEach(this::cancelIfPending);
    }

    private void cancelIfPending(Guest guest) {
        if (guest.isInvitationPending()) {
            guest.cancelInvitation();
        }
    }

    public void addRepresentative(Guest guest) {
        representatives.add(guest);
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

    public Boolean isRepresentative(Guest guest) {
        return representatives.stream().anyMatch(representative -> representative.equals(guest));
    }

}
