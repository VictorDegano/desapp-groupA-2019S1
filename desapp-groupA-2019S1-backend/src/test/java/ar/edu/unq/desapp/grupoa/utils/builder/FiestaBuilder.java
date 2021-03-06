package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FiestaBuilder {

    private Fiesta fiesta;

    private FiestaBuilder(){
        this.fiesta  = new Fiesta();
        this.fiesta.setGoodsForGuest(new ArrayList<Good>());
        this.fiesta.setGuest(new ArrayList<Guest>());
    }

    public static FiestaBuilder buildAFiesta(){
        return new FiestaBuilder();
    }

    public FiestaBuilder withOrganizer(User user){
        this.fiesta.setOrganizer(user);
        return this;
    }

    public FiestaBuilder addGuest(Guest aGuest){
        this.fiesta.getGuest()
                   .add(aGuest);
        return this;
    }

    public FiestaBuilder addGood(Good aGood){
        this.fiesta.getGoodsForGuest()
                   .add(aGood);
        return this;
    }

    public FiestaBuilder withName(String name){
        this.fiesta.setName(name);
        return this;
    }

    public FiestaBuilder withLimitConfirmationDateTime(LocalDateTime limitConfirmationDate){
        this.fiesta.setLimitConfirmationDateTime(limitConfirmationDate);
        return this;
    }

    public FiestaBuilder withGoods(List<Good> goods){
        this.fiesta.setGoodsForGuest(goods);
        return this;
    }


    public FiestaBuilder withConfirmations(Integer quantityOfConfirmations){
        this.fiesta.setConfirmations(quantityOfConfirmations);
        return this;
    }

    public FiestaBuilder withClosedState(){
        this.fiesta.setStatus(EventStatus.CLOSE);
        return this;
    }

    public FiestaBuilder withOpenState(){
        this.fiesta.setStatus(EventStatus.OPEN);
        return this;
    }

    public FiestaBuilder withCreationDate(LocalDateTime creationDate) {
        this.fiesta.setCreationDate(creationDate);
        return this;
    }

    public Fiesta build(){
        return this.fiesta;
    }

}
