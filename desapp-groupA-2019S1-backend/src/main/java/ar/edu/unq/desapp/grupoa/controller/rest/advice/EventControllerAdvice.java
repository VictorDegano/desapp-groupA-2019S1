package ar.edu.unq.desapp.grupoa.controller.rest.advice;

import ar.edu.unq.desapp.grupoa.controller.rest.EventController;
import ar.edu.unq.desapp.grupoa.exception.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(assignableTypes = EventController.class)
public class EventControllerAdvice extends GeneralControllerAdvice {

    @ExceptionHandler({EventNotFoundException.class})
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException e) {
        return error(NOT_FOUND, e);
    }


}
