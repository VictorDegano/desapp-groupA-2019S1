package ar.edu.unq.desapp.grupoa.utils.builders;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Good;
import ar.edu.unq.desapp.grupoa.model.Guest;
import ar.edu.unq.desapp.grupoa.model.stateFiesta.CloseFiesta;
import ar.edu.unq.desapp.grupoa.model.stateFiesta.OpenFiesta;
import ar.edu.unq.desapp.grupoa.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public FiestaBuilder withConfirmations(Integer quantityOfConfirmations){
        this.fiesta.setConfirmations(quantityOfConfirmations);
        return this;
    }

    public FiestaBuilder withClosedState(){
        this.fiesta.setState(new CloseFiesta(this.fiesta));
        return this;
    }

    public FiestaBuilder withOpenState(){
        this.fiesta.setState(new OpenFiesta(this.fiesta));
        return this;
    }

    public Fiesta build(){
        return this.fiesta;
    }
}
