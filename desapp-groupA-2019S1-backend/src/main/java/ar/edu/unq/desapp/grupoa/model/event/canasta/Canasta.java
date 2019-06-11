package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.*;
import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Canasta extends Event {

    public static Canasta createWithATemplate(String name, User organizer, List<Guest> guests, Template template, LocalDateTime creationDate) {
        if(!template.isForEvent(EventType.CANASTA)){
            throw new InvalidTemplateException(EventType.CANASTA, template.getEventType());
        }
        return new Canasta(name, organizer, guests, template.getGoodsForEvent(), creationDate);
    }

    @Override
    public boolean eventIsClosed() {
        return this.status.equals(EventStatus.CLOSE);
    }

    @Override
    public Integer totalCost() {
        return this.getGoodsForGuest()
            .stream()
            .mapToInt(Good::totalCost)
            .sum();

    }

    @Override
    public void confirmAsistancesOf(Guest guestToAssist) {
        this.confirmUser(guestToAssist.getUser());
    }

    @Override
    public EventType getType() {
        return EventType.CANASTA;
    }

    public void confirmUser(User userToConfirmAssistance) {
        if(this.eventIsClosed()){
            throw new ConfirmAsistanceException(this,userToConfirmAssistance);
        }
        this.getGuestOfUser(userToConfirmAssistance).confirmAsistance();
    }

    public void ownAGood(User user, CanastaGood good) {
        if(this.eventIsClosed()){
            throw new CanastaCloseException(this.getName(),user.getFirstName());
        }
        OwnershipValidator.ownAGood(user,good,this);
    }

    @Override
    public void close() {
        this.setStatus(EventStatus.CLOSE);
        this.cancelPendingInvitations();
        this.chargeTheExpenses();
    }

    private void chargeTheExpenses() {
        this.getGoodsForGuest().forEach((good) -> {
            if (good.hasOwner()) {
                good.getUserThatOwnsTheGood().extract(good.totalCost());
            } else{
                this.getOrganizer().extract(good.totalCost());
            }});
    }

    private void cancelPendingInvitations() {
        this.getGuest().forEach((guest) -> { if(guest.isInvitationPending()){ guest.cancelInvitation();}});
    }

    public Guest getGuestOfUser(User userToConfirmAssistance) {
        try{
            return this.getGuest().stream().filter(guest1 -> guest1.getUser()==userToConfirmAssistance).collect(Collectors.toList()).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ConfirmAsistanceException(this,userToConfirmAssistance);
        }
    }

    /**[}-{]---------------------------------------------[}-{]
     [}-{]----------------[CONSTRUCTORS]---------------[}-{]
     [}-{]---------------------------------------------[}-{]**/
    public Canasta(){   }

    public Canasta(String name, User organizer, List<Guest> guests, List<Good> goods, LocalDateTime creationDate) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuest(guests);
        this.setGoodsForGuest(goods);
        this.setStatus(EventStatus.OPEN);
        this.setCreationDate(creationDate);
    }
}
