package com.x.broker.rest;

import com.x.broker.domain.ErrorMessage;
import org.hibernate.JDBCException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A global exception handler for fatal errors.
 *
 * @author Akis Papadopoulos
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage(504, "Fatal error, binding problem occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String method = exc.getHttpMethod();
        String url = exc.getRequestURL();

        ErrorMessage error = new ErrorMessage(504, "Fatal error, no handler found for "
                + method + " on " + url);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String method = exc.getMethod();

        ErrorMessage error = new ErrorMessage(405, "Fatal error, method " + method + " is not allowed");

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = exc.getParameterName();

        ErrorMessage error = new ErrorMessage(400, "Bad request, parameter " + name + " is missing");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object value = exc.getValue();
        String type = exc.getRequiredType().getSimpleName();

        ErrorMessage error = new ErrorMessage(400, "Bad request, type mismatch for input "
                + value + " must be of type " + type);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exc) {
        String name = exc.getName();
        String type = exc.getRequiredType().getSimpleName();

        ErrorMessage error = new ErrorMessage(400, "Bad request, type mismatch for input "
                + name + " must be of type " + type);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage(400, "Bad request, invalid argument form");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String type = exc.getContentType().toString();

        ErrorMessage error = new ErrorMessage(415, "Bad request, media type "
                + type + " is not supported");

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exc,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage(406, "Bad request, media type is not acceptable");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(JDBCException.class)
    protected ResponseEntity<?> handleDatabaseFatalError(JDBCException exc) {
        ErrorMessage error = new ErrorMessage(504, "Fatal error, service is not available");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
