package ar.edu.unq.desapp.grupoa.model.statefiesta;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;

public abstract class FiestaState {

    private Fiesta fiesta;

    abstract public void confirmAssistanceOf(Guest aGuest);

    public abstract boolean isClosed();

    public abstract FiestaState nextState();

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Fiesta getFiesta() { return fiesta;  }
    public void setFiesta(Fiesta fiesta) {  this.fiesta = fiesta;   }

}
