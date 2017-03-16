package com.x.broker.rest;

import com.x.broker.domain.ErrorMessage;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A global exception handler for fatal errors.
 *
 * @author Akis Papadopoulos
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(JDBCException.class)
    protected ResponseEntity<?> handleFatalError(Exception exc) {
        ErrorMessage error = new ErrorMessage(504, "Service is not available, a fatal error occurred");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
