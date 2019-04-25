package ar.edu.unq.desapp.grupoa.testUtils;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Good;
import ar.edu.unq.desapp.grupoa.model.Guest;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FiestaBuilder {

    private Fiesta fiesta;

    private FiestaBuilder(){
        this.fiesta  = new Fiesta();
        this.fiesta.setGoodsForGuest(new ArrayList<Good>());
        this.fiesta.setGuest(new ArrayList<Guest>());
    }

    public static FiestaBuilder buildAGuest(){
        return new FiestaBuilder();
    }

    public FiestaBuilder withOrganizer(String name){
        this.fiesta.setOrganizer(name);
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

    public FiestaBuilder withLimitConfirmationDateTime(LocalDateTime limitConfirmationDate){
        this.fiesta.setLimitConfirmationDateTime(limitConfirmationDate);
        return this;
    }

    public FiestaBuilder withConfirmations(Integer quantityOfConfirmations){
        this.fiesta.setConfirmations(quantityOfConfirmations);
        return this;
    }

    public Fiesta build(){
        return this.fiesta;
    }
}
