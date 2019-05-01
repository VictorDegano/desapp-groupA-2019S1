package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import java.time.LocalDateTime;

// TODO: 27/4/2019 Al momento que se use para el resto de los eventos hay que hacer algunos cambios sintacticos
public class ConfirmationLimitException extends RuntimeException {

    private String fiestaName;
    private LocalDateTime timeLimitToConfirm;

    public ConfirmationLimitException(Fiesta fiesta){
        this.fiestaName = fiesta.getName();
        this.timeLimitToConfirm = fiesta.getLimitConfirmationDateTime();
    }

    @Override
    public String getMessage() {
        return String.format("No se puede confirmar la asistenciaa la fiesta %s, la fecha limite era %s",
                             this.fiestaName,
                             this.timeLimitToConfirm);
    }
}
