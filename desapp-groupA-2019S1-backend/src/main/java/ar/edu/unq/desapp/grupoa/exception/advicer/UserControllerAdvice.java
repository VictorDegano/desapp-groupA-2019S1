package ar.edu.unq.desapp.grupoa.exception.advicer;

import ar.edu.unq.desapp.grupoa.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UserControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(UserNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        LOGGER.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }

}
