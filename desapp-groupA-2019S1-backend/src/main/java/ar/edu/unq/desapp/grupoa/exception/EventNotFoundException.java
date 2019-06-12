package ar.edu.unq.desapp.grupoa.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Integer eventId){
        super(String.format("The Event with the id: %s not found", eventId.toString()));
    }

}
