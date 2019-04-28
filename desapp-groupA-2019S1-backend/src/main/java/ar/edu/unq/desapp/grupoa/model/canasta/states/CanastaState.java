package ar.edu.unq.desapp.grupoa.model.canasta.states;

import ar.edu.unq.desapp.grupoa.model.Canasta;

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
