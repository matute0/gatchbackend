package org.example.gatchbackend.exceptions.user;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException() {
        super("This email already in use");
    }
}
