package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Baquita extends Event {

    private  BaquitaState state;

    public Baquita(String name, User organizer, List<Guest> guests, List<Good> goodsForGuest){
        this.name = name;
        this.organizer = organizer;
        this.guests = guests;
        this.goodsForGuest = goodsForGuest;
        this.state = BaquitaState.OPEN;
    }

    public String getName(){
        return name;
    }

    public Boolean eventIsClosed(){
        return state.equals(BaquitaState.CLOSE);
    }

    public void close(){
        this.state = BaquitaState.CLOSE;
    }

    public Integer totalCost(){return null;}


    public void addGuest(Guest guest){
        guests.add(guest);
    }


    public void confirmAsistanceOf(User guestToAssist) {
    }

    public void addGood(Good goodToAdd){} //Se podra agregar mas goods una vez creada?¿

    public User getOrganizer() {
        return organizer;
    }

    public List<Guest> getGuests() {
        return new ArrayList<>(guests);
    }

    public List<Good> getGoodsForGuest() {
        return new ArrayList<>(goodsForGuest);
    }



    @Override
    protected Guest inviteUser(User userToInvite) {
        return null;
    }
}
