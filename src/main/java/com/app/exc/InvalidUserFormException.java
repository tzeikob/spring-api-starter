package com.app.exc;

/**
 * A invalid user form runtime exception.
 *
 * @author Akis Papadopoulos
 */
public class InvalidUserFormException extends RuntimeException {

    public InvalidUserFormException(String message) {
        super(message);
    }
}
