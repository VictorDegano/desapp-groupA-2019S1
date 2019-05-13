package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.Event;

public class CloseEventException extends RuntimeException {


    public CloseEventException(Event event) {
        super(String.format("The Event %s is closed, no more confirmations are accepted", event.getName()));
    }
}
