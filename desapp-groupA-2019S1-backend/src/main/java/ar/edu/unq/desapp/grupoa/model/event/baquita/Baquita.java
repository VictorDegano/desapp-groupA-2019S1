package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "baquita_type")
public abstract class Baquita extends Event {

    public Baquita(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest, LocalDateTime creationDate){
        this.name = name;
        this.organizer = organizer;
        this.guests = guests;
        this.goodsForGuest = goodsForGuest;
        this.status = EventStatus.OPEN;
        this.setCreationDate(creationDate);
    }

    public Baquita() {
    }

    @Override
    public boolean eventIsClosed() {
        return this.status.equals(EventStatus.CLOSE);
    }

    @Override
    public void close() {
        this.status = EventStatus.CLOSE;
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
