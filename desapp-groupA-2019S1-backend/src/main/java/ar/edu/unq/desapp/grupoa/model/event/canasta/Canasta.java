package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.exception.event.*;
import ar.edu.unq.desapp.grupoa.model.event.*;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CanastaState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.state.CanastaStateInPreparation;
import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Canasta extends Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private CanastaState canastaState;

    // TODO: 3/5/2019 metodos a definir!
    @Override
    public boolean eventIsClosed() {    return false;   }

    @Override
    public void close() {   }

    @Override
    public Integer totalCost() {    return null;    }

    @Override
    public void confirmAsistancesOf(Guest guestToAssist) {  }


    public static Canasta createWithATemplate(String name, User organizer, List<Guest> guests, Template template) {
        if(!template.isForEvent(EventType.CANASTA)){
            throw new InvalidTemplateException(EventType.CANASTA, template.getEventType());
        }
        return new Canasta(name, organizer, guests, template.getGoodsForEvent());
    }

    public Canasta(String name, User organizer) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuest(new ArrayList<>());
        this.setGoodsForGuest(new ArrayList<>());
        this.setState(new CanastaStateInPreparation());
    }

    public Canasta(String name, User organizer, List<Guest> guests, List<Good> goods) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuest(guests);
        this.setGoodsForGuest(goods);
        this.setState(new CanastaStateInPreparation());
    }

    public CanastaState getState() {
        return this.canastaState;
    }

    public void setState(CanastaState aCanastaState) {
        this.canastaState = aCanastaState;
    }

    //easy way, refactor this
    public void confirmUser(User userToConfirmAssistance) {
        Guest guestToConfirmAssistance;

        if(this.getState().isCloseCanasta()){
            throw new ConfirmAsistanceException(this.getName(),userToConfirmAssistance.getFirstName());
        }

        try{
            guestToConfirmAssistance = this.getGuest().stream().filter(guest1 -> guest1.getUser()==userToConfirmAssistance).collect(Collectors.toList()).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ConfirmAsistanceException(this.getName(),userToConfirmAssistance.getFirstName());
        }
        guestToConfirmAssistance.confirmAsistance();
    }

    public void ownAGood(User user, CanastaGood good) {
        Guest guest = this.getGuest().stream().filter(guest1 -> guest1.getUser()==user).collect(Collectors.toList()).get(0);

        if(this.getState().isCloseCanasta()){
            throw new CanastaCloseException(this.getName(),guest.getUser().getFirstName());
        }

        if((guest.getConfirmAsistance() != InvitationState.ACCEPTED)){
            throw new OwnAGoodWithAnUnconfirmedGuestException(this.getName(),user.getFirstName());
        }
        if( good.getUserThatOwnsTheGood() == null){
            this.getGuest().stream().filter(guest1 -> guest1.getUser()==user).collect(Collectors.toList()).get(0).ownAGood(good);
        }else{
            throw new GoodAlreadyOwnedException(this.getName(),user.getFirstName());
        }
    }
}
