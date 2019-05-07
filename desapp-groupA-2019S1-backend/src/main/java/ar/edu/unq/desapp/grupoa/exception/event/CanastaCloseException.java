package ar.edu.unq.desapp.grupoa.exception.event;

public class CanastaCloseException extends RuntimeException {

    public CanastaCloseException(String canastaName, String guestName){
        super(String.format("Error al posser el gasto. La Fiesta: %s  esta cerrada, el invitado: %s no puede poseer el gasto",
                canastaName,
                guestName));
    }

}
