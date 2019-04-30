package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;

// TODO: 27/4/2019 Al momento que se use para el resto de los eventos hay que hacer algunos cambios sintacticos
public class CloseEventException extends RuntimeException {

    private String fiestaName;

    public CloseEventException(Fiesta fiesta) {
        this.fiestaName = fiesta.getName();
    }

    @Override
    public String getMessage() {
        return String.format("La fiestaName %s esta cerrada, no se Aceptan mas confirmaciones", this.fiestaName);
    }
}
