package ar.edu.unq.desapp.grupoa.exception;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.model.Guest;

// TODO: 27/4/2019 Al momento que se use para el resto de los eventos hay que hacer algunos cambios sintacticos
public class ConfirmAsistanceException extends RuntimeException {

    private String fiestaName;
    private String guestName;

    public ConfirmAsistanceException(Fiesta fiesta, Guest guest){
        super();
        this.fiestaName = fiesta.getName();
        this.guestName = guest.name();
    }
    //TODO: hay que definir si se deja esta sobrecarga del metodo o se elige uno de los dos
    public ConfirmAsistanceException(String fiesta, String guest){
        super();
        this.fiestaName = fiesta;
        this.guestName = guest;
    }

    @Override
    public String getMessage(){
        return String.format("Error al confirmar la invitacion. La Fiesta: %s no tiene como invitado a %s",
                             this.fiestaName,
                             this.guestName);
    }
}
