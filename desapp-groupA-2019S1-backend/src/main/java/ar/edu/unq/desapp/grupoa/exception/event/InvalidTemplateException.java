package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class InvalidTemplateException extends RuntimeException{

    public InvalidTemplateException(EventType eventTypeToCreate, EventType eventTypeOfTemplate){
        super(String.format("El template que quiere usar es para %s y el evento que quiere crear es una %s",
                eventTypeToCreate.name().toLowerCase(),
                eventTypeOfTemplate.name().toLowerCase()));
    }
}
