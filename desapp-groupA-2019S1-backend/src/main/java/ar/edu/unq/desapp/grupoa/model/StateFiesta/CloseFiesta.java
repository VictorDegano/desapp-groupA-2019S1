package ar.edu.unq.desapp.grupoa.model.StateFiesta;

import ar.edu.unq.desapp.grupoa.exception.CloseEventException;
import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;

public class CloseFiesta extends FiestaState {

    @Override
    public void confirmAssistanceOf(Guest aGuest) {
        throw new CloseEventException(this.getFiesta());
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public CloseFiesta(Fiesta aFiesta){
        this.setFiesta(aFiesta);
    }
}
