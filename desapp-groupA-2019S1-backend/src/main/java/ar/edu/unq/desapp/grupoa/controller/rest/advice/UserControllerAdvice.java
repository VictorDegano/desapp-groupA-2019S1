package ar.edu.unq.desapp.grupoa.controller.rest.advice;

import ar.edu.unq.desapp.grupoa.controller.rest.UserController;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice extends GeneralControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(UserNotFoundException e) {
        return error(NOT_FOUND, e);
    }

}
