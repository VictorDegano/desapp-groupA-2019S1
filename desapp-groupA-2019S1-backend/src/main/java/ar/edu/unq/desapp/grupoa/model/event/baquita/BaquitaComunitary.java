package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.deposit;

@Entity
@DiscriminatorValue("Comunitary")
public class BaquitaComunitary extends Baquita {

    public BaquitaComunitary(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest, LocalDateTime creationDate) {
        super(name, organizer, guests, goodsForGuest, creationDate);
    }

    public BaquitaComunitary() {
    }

    public static BaquitaComunitary createWithATemplate(String name, User organizer, List<Guest> guests, Template template, LocalDateTime creationDate) {
        return new BaquitaComunitary(name, organizer, guests, template.getGoodsForEvent(), creationDate);
    }

    @Override
    protected void distributePayment() {
        Integer priceToPay = totalCost() / guests.size();

        List<Guest> guests = getConfirmedGuests();
        guests = guestThatCanPay(guests,priceToPay);

        if (!guests.isEmpty()) {
            guests.forEach(guest -> pay(priceToPay, guest.getUser()));
            depositAmount(totalCost(), organizer);
        }
    }



    private void depositAmount(Integer priceToPay, User organizer) {
        deposit(organizer.getAccount(), priceToPay);
    }

    @Override
    public Integer totalCost() {
        return goodsForGuest.stream().mapToInt(Good::totalCost).sum();
    }

    @Override
    public EventType getType() {
        return EventType.BAQUITA_COMUNITARY;
    }
}
