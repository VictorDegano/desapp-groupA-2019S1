package ar.edu.unq.desapp.grupoa.model.stateFiesta;

import ar.edu.unq.desapp.grupoa.exception.CloseEventException;
import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;

public class CloseFiesta extends FiestaState {

    @Override
    public void confirmAssistanceOf(Guest aGuest) {
        throw new CloseEventException(this.getFiesta());
    }

    @Override
    public boolean isClosed() {
        return true;
    }

    @Override
    public FiestaState nextState() {
        return this;
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public CloseFiesta(Fiesta aFiesta){
        this.setFiesta(aFiesta);
    }
}
