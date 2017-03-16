package com.x.broker.rest;

import com.x.broker.domain.ErrorMessage;
import com.x.broker.exc.DuplicateUsernameException;
import com.x.broker.exc.InvalidUserFormException;
import com.x.broker.exc.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A resource exception handler for user entities.
 *
 * @author Akis Papadopoulos
 */
@ControllerAdvice
public class UserResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleNotFound(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(404, exc.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    protected ResponseEntity<?> handleConfilct(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(409, exc.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidUserFormException.class)
    protected ResponseEntity<?> handleBadRequest(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(400, exc.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
