package ar.edu.unq.desapp.grupoa.model;

public class ConfirmAsistanceException extends RuntimeException {

    private String fiestaName;
    private String guestName;

    public ConfirmAsistanceException(String fiestaName, String guestName){
        this.fiestaName = fiestaName;
        this.guestName = guestName;
    }

    @Override
    public String getMessage(){
        return String.format("Error al confirmar la invitacion. La Fiesta: %s no tiene como invitado a %s",
                             this.fiestaName,
                             this.guestName);
    }
}
