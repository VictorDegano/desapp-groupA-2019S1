package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.*;
import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CanastaState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CanastaStateInPreparation;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CloseCanasta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Canasta extends Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private CanastaState canastaState;

    public static Canasta createWithATemplate(String name, User organizer, List<Guest> guests, Template template) {
        if(!template.isForEvent(EventType.CANASTA)){
            throw new InvalidTemplateException(EventType.CANASTA, template.getEventType());
        }
        return new Canasta(name, organizer, guests, template.getGoodsForEvent());
    }

    @Override
    public boolean eventIsClosed() {    return this.canastaState.isCloseCanasta();   }

    // todo falta calcular el costo total
    @Override
    public Integer totalCost() {    return null;    }

    @Override
    public void confirmAsistancesOf(Guest guestToAssist) {
        this.confirmUser(guestToAssist.getUser());
    }

    public void confirmUser(User userToConfirmAssistance) {
        this.getState().confirmUser(userToConfirmAssistance);
    }

    public void ownAGood(User user, CanastaGood good) {
        this.getState().ownAGood(user,good);
    }

    @Override
    public void close() {
        this.changeStateToClose();
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

    private void changeStateToClose() {
        this.setState(new CloseCanasta(this));
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

    public Canasta(String name, User organizer, List<Guest> guests, List<Good> goods) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuest(guests);
        this.setGoodsForGuest(goods);
        this.setState(new CanastaStateInPreparation(this));
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public CanastaState getState() {    return this.canastaState;   }
    public void setState(CanastaState aCanastaState) {  this.canastaState = aCanastaState;  }
}
