package ar.edu.unq.desapp.grupoa.exception;

public class GoodAlreadyOwnedException extends RuntimeException {

    private String canastaName;
    private String guestName;

    public GoodAlreadyOwnedException(String canastaName, String guestName){
        this.canastaName = canastaName;
        this.guestName = guestName;
    }

    @Override
    public String getMessage(){
        return String.format("Error al posser el gasto en la canasta: %s. El invitado: %s no puede " +
                        "poseer un gasto que ya esta poseiiiido",
                this.canastaName,
                this.guestName);
    }

}

