package org.example.gatchbackend.exceptions.user;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("This username already in use");
    }
}
