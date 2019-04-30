package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.exception.CanastaCloseException;
import ar.edu.unq.desapp.grupoa.exception.GoodAlreadyOwnedException;
import ar.edu.unq.desapp.grupoa.exception.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.exception.OwnAGoodWithAnUnconfirmedGuestException;
import ar.edu.unq.desapp.grupoa.model.canasta.states.CanastaState;
import ar.edu.unq.desapp.grupoa.model.canasta.states.CanastaStateInPreparation;
import ar.edu.unq.desapp.grupoa.model.canasta.states.CloseCanasta;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Canasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Transient
    private User organizer;
    @Transient
    private List<Guest> guests;
    @Transient
    private List<CanastaGood> goods;
    @Transient
    private CanastaState canastaState;


    public Canasta(String name, User organizer) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuests(new ArrayList<>());
        this.setGoods(new ArrayList<>());
        this.setState(new CanastaStateInPreparation());
    }

    public Canasta(String name, User organizer, List<Guest> guests, List<CanastaGood> goods) {
        this.setName(name);
        this.setOrganizer(organizer);
        this.setGuests(guests);
        this.setGoods(goods);
        this.setState(new CanastaStateInPreparation());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<CanastaGood> getGoods() {
        return goods;
    }

    public void setGoods(List<CanastaGood> goods) {
        this.goods = goods;
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
            throw new ConfirmAsistanceException(this.name,userToConfirmAssistance.getFirstName());
        }

        try{
            guestToConfirmAssistance = guests.stream().filter(guest1 -> guest1.getUser()==userToConfirmAssistance).collect(Collectors.toList()).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ConfirmAsistanceException(this.name,userToConfirmAssistance.getFirstName());
        }
        guestToConfirmAssistance.confirmAsistance();
    }

    public void ownAGood(User user, CanastaGood good) {
        Guest guest = guests.stream().filter(guest1 -> guest1.getUser()==user).collect(Collectors.toList()).get(0);

        if(this.getState().isCloseCanasta()){
            throw new CanastaCloseException(this.getName(),guest.getUser().getFirstName());
        }

        if((guest.getConfirmAsistance() != InvitationState.ACCEPTED)){
            throw new OwnAGoodWithAnUnconfirmedGuestException(this.name,user.getFirstName());
        }
        if( good.getUserThatOwnsTheGood() == null){
        guests.stream().filter(guest1 -> guest1.getUser()==user).collect(Collectors.toList()).get(0).ownAGood(good);
        }else{
            throw new GoodAlreadyOwnedException(this.getName(),user.getFirstName());
        }
    }

    public void closeCanasta() {
        this.setState(new CloseCanasta());
        this.guests.forEach((guest) -> { if(guest.isInvitationPending()){ guest.cancelInvitation();}});
        this.goods.forEach((good) -> {
            if (good.getUserThatOwnsTheGood() != null) {
                good.getUserThatOwnsTheGood().extract(good.totalCost());
            } else{
                this.getOrganizer().extract(good.totalCost());
                 }});

    }
}
