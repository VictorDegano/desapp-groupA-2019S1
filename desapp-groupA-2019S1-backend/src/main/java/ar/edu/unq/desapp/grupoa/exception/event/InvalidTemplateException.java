package ar.edu.unq.desapp.grupoa.exception.event;

import ar.edu.unq.desapp.grupoa.model.event.EventType;

public class InvalidTemplateException extends RuntimeException{

    private String eventTypeToCreate;
    private String eventTypeOfTemplate;

    public InvalidTemplateException(EventType eventTypeToCreate, EventType eventTypeOfTemplate){
        this.eventTypeToCreate  = eventTypeToCreate.name().toLowerCase();
        this.eventTypeOfTemplate = eventTypeOfTemplate.name().toLowerCase();
    }

    @Override
    public String getMessage() {
        return String.format("El template que quiere usar es para %s y el evento que quiere crear es una %s",
                             this.eventTypeOfTemplate,
                             this.eventTypeToCreate);
    }
}
