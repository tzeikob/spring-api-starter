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
    protected ResponseEntity<ErrorMessage> handleNotFound(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(404, exc.getMessage());

        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    protected ResponseEntity<ErrorMessage> handleConfilct(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(409, exc.getMessage());

        return new ResponseEntity<ErrorMessage>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUserFormException.class)
    protected ResponseEntity<ErrorMessage> handleBadRequest(RuntimeException exc) {
        ErrorMessage error = new ErrorMessage(400, exc.getMessage());

        return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
    }
}
