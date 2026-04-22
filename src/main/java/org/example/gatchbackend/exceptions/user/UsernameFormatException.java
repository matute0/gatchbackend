package org.example.gatchbackend.exceptions.user;

public class UsernameFormatException extends RuntimeException {
    public UsernameFormatException() {
        super("Username not valid");
    }
}
