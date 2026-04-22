package org.example.gatchbackend.exceptions.user;

public class EmailFormatException extends RuntimeException {
    public EmailFormatException() {
        super("Email not valid");
    }
}
