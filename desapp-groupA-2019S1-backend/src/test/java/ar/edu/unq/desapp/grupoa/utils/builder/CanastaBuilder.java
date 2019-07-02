package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CanastaBuilder {

    private Canasta canasta;

    private CanastaBuilder(){
        this.canasta = new Canasta();
        this.canasta.setGoodsForGuest(new ArrayList<>());
        this.canasta.setGuest(new ArrayList<>());
    }

    public static CanastaBuilder buildCanasta(){
        return new CanastaBuilder().withOpenState();
    }

    public CanastaBuilder withOrganizer(User user){
        this.canasta.setOrganizer(user);
        return this;
    }

    public CanastaBuilder addGuest(Guest aGuest){
        this.canasta.getGuest()
                   .add(aGuest);
        return this;
    }

    public CanastaBuilder addGood(CanastaGood aCanastaGood){
        this.canasta.getGoodsForGuest()
                   .add(aCanastaGood);
        return this;
    }

    public CanastaBuilder withName(String name){
        this.canasta.setName(name);
        return this;
    }


    public CanastaBuilder withClosedState(){
        this.canasta.setStatus(EventStatus.CLOSE);
        return this;
    }

    public CanastaBuilder withOpenState(){
        this.canasta.setStatus(EventStatus.OPEN);
        return this;
    }

    public CanastaBuilder withCreationDate(LocalDateTime creationDate) {
        this.canasta.setCreationDate(creationDate);
        return this;
    }

    public Canasta build(){
        return this.canasta;
    }

    public CanastaBuilder withGoods(List<Good> canastaGoods) {
        this.canasta.setGoodsForGuest(canastaGoods);
        return this;
    }
}
