package com.app.exc;

/**
 * A user not found runtime exception.
 *
 * @author Akis Papadopoulos
 */
public class UserNotFoundException extends RuntimeException {

    private Long id;

    private String username;

    public UserNotFoundException(Long id) {
        super("User identified by '" + id + "' not found");

        this.id = id;
    }

    public UserNotFoundException(String username) {
        super("User with username '" + username + "' not found");

        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
