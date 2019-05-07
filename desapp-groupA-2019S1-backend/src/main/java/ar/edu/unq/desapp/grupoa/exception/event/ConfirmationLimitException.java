package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import java.time.LocalDateTime;

public class ConfirmationLimitException extends RuntimeException {

    public ConfirmationLimitException(Event event, LocalDateTime time){
        super(String.format("Cannot confirm the assist to the event %s, the limit date was %s",
                event.getName(),
                time));
    }
}
