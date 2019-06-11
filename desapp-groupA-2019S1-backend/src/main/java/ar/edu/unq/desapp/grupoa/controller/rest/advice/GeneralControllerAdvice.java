package ar.edu.unq.desapp.grupoa.controller.rest.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GeneralControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return this.error(INTERNAL_SERVER_ERROR, e);
    }

    ResponseEntity<String> error(HttpStatus status, Exception e) {
        LOGGER.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }

}
