package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;

public class LoadedGood {

    private Guest guest;
    private Good  good ;

    public LoadedGood(Guest guest,Good good) {
        this.guest =guest;
        this.good = good;
    }

    public Integer getAmount(){
        return  good.totalCost();
    }

    public Object getGood() {
        return good;
    }

    public Guest getGuest() {
        return guest;
    }
}
