package ar.edu.unq.desapp.grupoa.model.event.canasta.state;


import ar.edu.unq.desapp.grupoa.exception.event.CanastaCloseException;
import ar.edu.unq.desapp.grupoa.exception.event.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class CloseCanasta extends CanastaState {

    public CloseCanasta(Canasta aCanasta) {
        super(aCanasta);
    }

    @Override
    public void ownAGood(User user, CanastaGood good) {
            throw new CanastaCloseException(this.getCanasta().getName(),user.getFirstName());
    }

    @Override
    public Boolean isCloseCanasta(){
        return true;
    }

    @Override
    public void confirmUser(User userToConfirmAssistance) {
            throw new ConfirmAsistanceException(this.getCanasta(),userToConfirmAssistance);

    }
}
