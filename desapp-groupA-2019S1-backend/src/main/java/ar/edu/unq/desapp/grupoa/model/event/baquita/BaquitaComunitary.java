package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.deposit;

public class BaquitaComunitary extends Baquita {

    public BaquitaComunitary(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest, LocalDateTime creationDate) {
        super(name, organizer, guests, goodsForGuest,creationDate);
    }

    public static BaquitaComunitary createWithATemplate(String name, User organizer, List<Guest> guests, Template template, LocalDateTime creationDate) {
       return new BaquitaComunitary(name,organizer,guests,template.getGoodsForEvent(),creationDate);
    }

    @Override
    protected void distributePayment() {
        List<Guest> guests = getConfirmedGuests();
        if (!guests.isEmpty()) {
            Integer priceToPay = totalCost() / guests.size();
            guests.forEach(guest -> pay(priceToPay, guest.getUser()));
            depositAmount(totalCost(), organizer);
        }
    }

    //TODO: Esto deberia ser un behaviour, cuando toque crear los services lo paso alla.
    private void depositAmount(Integer priceToPay, User organizer) {
        Account account = deposit(organizer.getAccount(), priceToPay);
        organizer.updateAccount(account);
    }

    @Override
    public Integer totalCost() {
        return goodsForGuest.stream().mapToInt(Good::totalCost).sum();
    }
}
