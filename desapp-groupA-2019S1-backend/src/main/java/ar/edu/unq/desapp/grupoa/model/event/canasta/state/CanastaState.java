package ar.edu.unq.desapp.grupoa.model.event.canasta.state;

import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;

public abstract class CanastaState {

    protected Canasta canasta;

    protected void setCanasta(Canasta aCanasta) {
        this.canasta = aCanasta;
    }

    public Boolean isInPreparationCanasta(){
        return false;
    }

    public Boolean isCloseCanasta(){
        return false;
    }
}
