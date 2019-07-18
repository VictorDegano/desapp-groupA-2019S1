package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("Representatives")
public class BaquitaRepresentatives extends Baquita {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Guest> representatives;

    @OneToMany(cascade = CascadeType.ALL)
    protected List<LoadedGood> loadedGoods;

    public BaquitaRepresentatives(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest, LocalDateTime creationDate) {
        super(name, organizer, guests, goodsForGuest, creationDate);
        this.representatives = new ArrayList<>();
        this.loadedGoods = new ArrayList<>();
    }

    public BaquitaRepresentatives() {
    }

    public static BaquitaRepresentatives createWithATemplate(String name, User organizer, List<Guest> guests, Template template, LocalDateTime creationDate) {
        return new BaquitaRepresentatives(name,organizer,guests,template.getGoodsForEvent(), creationDate);
    }

    protected void distributePayment() {
        List<Guest> guests = getConfirmedGuests();
        Integer priceToPay = totalCost() / (guests.size()+1);

        guests = guestThatCanPay(guests,priceToPay);
        guests.forEach(guest -> pay(priceToPay, guest.getUser()));
        pay(priceToPay,organizer);
    }

    protected List<Guest> getConfirmedGuests() {
        List<Guest> confirmedGuests = confirmed(this.guests);
        List<Guest> representatives = confirmed(this.representatives);

        confirmedGuests.addAll(representatives);
        return confirmedGuests;
    }

    public Guest getGuestOfUser(User userToConfirmAssistance) {
        List <Guest> allGuests = new ArrayList<>();
        allGuests.addAll(guests);
        allGuests.addAll(representatives);

        try{
            return allGuests.stream().filter(guest1 -> guest1.getUser()==userToConfirmAssistance).collect(Collectors.toList()).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ConfirmAsistanceException(this,userToConfirmAssistance);
        }
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

    public Integer totalCost() {
        return loadedGoods.stream().mapToInt(LoadedGood::getAmount).sum();
    }

    @Override
    public EventType getType() {
        return EventType.BAQUITA_REPRESENTATIVES;
    }

    public void loadGood(LoadedGood loadedGood){
        this.loadedGoods.add(loadedGood);
    }

    public Boolean goodIsloaded(Good good) {
        return this.loadedGoods.stream().anyMatch(loadedGood -> loadedGood.getGood().equals(good));
    }

    public List<Guest> getRepresentatives() {
        return representatives;
    }

    public List<LoadedGood> getLoadedGoods() {
        return loadedGoods;
    }
}
