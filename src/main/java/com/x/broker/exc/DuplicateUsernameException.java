package com.x.broker.exc;

/**
 * A duplicate username runtime exception.
 *
 * @author Akis Papadopoulos
 */
public class DuplicateUsernameException extends RuntimeException {

    private String username;

    public DuplicateUsernameException(String username) {
        super("Username '" + username + "' is already taken");

        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
