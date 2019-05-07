package ar.edu.unq.desapp.grupoa.model.event.canasta.state;

import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.canasta.OwnershipValidator;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class CanastaStateInPreparation extends CanastaState {

    public CanastaStateInPreparation(Canasta aCanasta) {
        super(aCanasta);
    }

    @Override
    public void ownAGood(User user, CanastaGood good) {
        OwnershipValidator.ownAGood(user,good,this.getCanasta());
    }

    @Override
    public Boolean isInPreparationCanasta(){
        return true;
    }

    @Override
    public void confirmUser(User userToConfirmAssistance) {

        this.getCanasta().getGuestOfUser(userToConfirmAssistance).confirmAsistance();

    }
}
