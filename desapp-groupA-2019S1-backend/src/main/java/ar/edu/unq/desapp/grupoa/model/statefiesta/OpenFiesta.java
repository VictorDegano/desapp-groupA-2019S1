package ar.edu.unq.desapp.grupoa.model.statefiesta;

import ar.edu.unq.desapp.grupoa.exception.ConfirmationLimitException;
import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;
import java.time.LocalDateTime;

public class OpenFiesta extends FiestaState {

    @Override
    public void confirmAssistanceOf(Guest aGuest) {
        if(this.acceptConfirmations()){
            this.getFiesta().completeConfirmationAsistance(aGuest);
        } else {
            throw new ConfirmationLimitException(this.getFiesta());
        }
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public FiestaState nextState() {
        return new CloseFiesta(this.getFiesta());
    }

    private Boolean acceptConfirmations() {
        return this.getFiesta().canConfirmInvitation(LocalDateTime.now());
    }

    public OpenFiesta(Fiesta aFiesta){
        this.setFiesta(aFiesta);
    }
}