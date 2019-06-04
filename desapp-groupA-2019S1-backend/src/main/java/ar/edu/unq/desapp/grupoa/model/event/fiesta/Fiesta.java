package ar.edu.unq.desapp.grupoa.model.event.fiesta;

import ar.edu.unq.desapp.grupoa.exception.event.CloseEventException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmationLimitException;
import ar.edu.unq.desapp.grupoa.exception.event.InvalidTemplateException;
import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Fiesta extends Event {

    private LocalDateTime limitConfirmationDateTime;
    private Integer confirmations;

    public static Fiesta createWithATemplate(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, LocalDateTime creationDate) {
        if (!template.isForEvent(EventType.FIESTA)) {
            throw new InvalidTemplateException(EventType.FIESTA, template.getEventType());
        }
        TemplateRelations.useTemplate(organizer,template);
        return new Fiesta(name, organizer, guests, limitTime, template.getGoodsForEvent(), creationDate);
    }

    @Override
    public void confirmAsistancesOf(Guest guestToAssist) {
        if(this.eventIsClosed()){
            throw new CloseEventException(this);
        }

        if(this.canConfirmInvitation(LocalDateTime.now())){
            this.completeConfirmationAsistance(guestToAssist);
        } else {
            throw new ConfirmationLimitException(this, this.getLimitConfirmationDateTime());
        }
    }

    public void completeConfirmationAsistance(Guest guestToAssist){
        if(isInvited(guestToAssist)){
            this.confirmations += 1;

            guestToAssist.confirmAsistance();

            updateFinalQuantityOfGoods();
        } else {
            throw new ConfirmAsistanceException(this, guestToAssist.getUser());
        }
    }

    private void updateFinalQuantityOfGoods() {
        this.getGoodsForGuest().forEach((Good good) -> good.multiplyFinalQuantityBy(this.confirmations));
    }

    @Override
    public Integer totalCost() {
        return this.getGoodsForGuest()
                .stream()
                .mapToInt(Good::totalCost)
                .sum();
    }

    // TODO: 4/5/2019 ¿Comportamiento de Evento?
    private boolean isInvited(Guest guestToAssist) {
        return this.getGuest().stream().anyMatch(guest -> guest.areThatGuest(guestToAssist));
    }

    public boolean canConfirmInvitation(LocalDateTime aLocalDateTimeToCompare) {
        return this.limitConfirmationDateTime.isAfter(aLocalDateTimeToCompare);
    }

    @Override
    protected boolean canInviteUser(){
        return !eventIsClosed() && (this.getLimitConfirmationDateTime().isAfter(LocalDateTime.now()));
    }

    @Override
    public boolean eventIsClosed() {
        return this.status.equals(EventStatus.CLOSE);
    }

    @Override
    public void close() {
        this.setStatus(EventStatus.CLOSE);
        cancelAllPendingInvitations();
    }

    // TODO: 4/5/2019 ¿Comportamiento de Evento?
    private void cancelAllPendingInvitations() {
        this.getGuest().forEach(this::cancelPendingInvitation);
    }

    // TODO: 4/5/2019 ¿Comportamiento de Evento?
    private void cancelPendingInvitation(Guest invitedGuest) {
        if(invitedGuest.isInvitationPending()){
            invitedGuest.cancelInvitation();
        }
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Fiesta() {}

    public Fiesta(String name, User organizer, List<Guest> guest, LocalDateTime limitConfirmationDateTime, List<Good> goodsForGuest, LocalDateTime creationDate) {
        this.setOrganizer(organizer);
        this.setGuest(guest);
        this.setGoodsForGuest(goodsForGuest);
        this.setName(name);
        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.confirmations = 0;
        this.setStatus(EventStatus.OPEN);
        this.setCreationDate(creationDate);
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public void setLimitConfirmationDateTime(LocalDateTime limitConfirmationDateTime) { this.limitConfirmationDateTime = limitConfirmationDateTime; }
    public LocalDateTime getLimitConfirmationDateTime() {   return this.limitConfirmationDateTime;   }

    public Integer getId() {    return this.id;  }

    public Integer getConfirmations() { return this.confirmations;   }

    @Override
    public EventType getType() {
        return EventType.FIESTA;
    }

    public void setConfirmations(Integer confirmations) {   this.confirmations = confirmations; }
}

