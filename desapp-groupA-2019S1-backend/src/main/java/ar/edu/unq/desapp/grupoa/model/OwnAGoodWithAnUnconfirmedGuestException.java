package ar.edu.unq.desapp.grupoa.model;

public class OwnAGoodWithAnUnconfirmedGuestException extends RuntimeException {
    private String canastaName;
    private String guestName;

    public OwnAGoodWithAnUnconfirmedGuestException(String canastaName, String guestName){
        this.canastaName = canastaName;
        this.guestName = guestName;
    }

    @Override
    public String getMessage(){
        return String.format("Error al posser el gasto. La Fiesta: %s no tiene como invitado a %s",
                this.canastaName,
                this.guestName);
    }
}
