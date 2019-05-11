package ar.edu.unq.desapp.grupoa.model.event.canasta.state;

import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;

public abstract class CanastaState {

    protected Canasta canasta;

    protected void setCanasta(Canasta aCanasta) {
        this.canasta = aCanasta;
    }

    public Canasta getCanasta() {
        return canasta;
    }

    public Boolean isInPreparationCanasta(){
        return false;
    }

    public Boolean isCloseCanasta(){
        return false;
    }

    public abstract void confirmUser(User userToConfirmAssistance);

    public CanastaState(Canasta aCanasta){
        this.setCanasta(aCanasta);
    }

    public abstract void ownAGood(User user, CanastaGood good);
}
