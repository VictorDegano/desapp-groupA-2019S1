package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class BaquitaRepresentatives extends Baquita {


    private List<Guest> representatives;

    public BaquitaRepresentatives(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest) {
        super(name, organizer, guests, goodsForGuest);
        this.representatives = new ArrayList<>();
    }

    protected void distributePayment() {
        List<Guest> guests = getConfirmedGuests();
        Integer priceToPay = totalCost() / (guests.size()+1);
        guests.forEach(guest -> pay(priceToPay, guest.getUser()));
        pay(priceToPay,organizer);
    }

    protected List<Guest> getConfirmedGuests() {
        List<Guest> confirmedGuests = confirmed(this.guests);
        List<Guest> representatives = confirmed(this.representatives);

        confirmedGuests.addAll(representatives);
        return confirmedGuests;
    }


    public void cancelPendingRepresentatives() {
        cancelGuests(this.representatives);
    }

    public void cancelPendingGuests() {
        cancelGuests(this.guests);
        cancelPendingRepresentatives();
    }

    public void addRepresentative(Guest guest) {
        representatives.add(guest);
    }

    public Boolean isInvited(Guest guest) {
        return guests.stream().anyMatch(baquitaGuest -> baquitaGuest.equals(guest)) ||
             isRepresentative(guest);
    }

    public Boolean isRepresentative(Guest guest) {
        return representatives.stream().anyMatch(representative -> representative.equals(guest));
    }

}
