package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;

public class LoadedGood {

    Guest   guest;
    Good    good;
    Integer amount;

    public LoadedGood(Guest guest, Good good, Integer amount) {
        this.guest =guest;
        this.good  =good;
        this.amount=amount;
    }

    public Integer getAmount(){
        return amount;
    }
}
