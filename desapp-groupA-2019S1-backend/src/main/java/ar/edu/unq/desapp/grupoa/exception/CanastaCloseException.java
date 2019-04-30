package ar.edu.unq.desapp.grupoa.exception;

public class CanastaCloseException extends RuntimeException {

    private String canastaName;
    private String guestName;

    public CanastaCloseException(String canastaName, String guestName){
        this.canastaName = canastaName;
        this.guestName = guestName;
    }

    @Override
    public String getMessage(){
        return String.format("Error al posser el gasto. La Fiesta: %s  esta cerrada, el invitado: %s no puede poseer el gasto",
                this.canastaName,
                this.guestName);
    }

}
