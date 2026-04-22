package org.example.gatchbackend.exceptions.user;

public class PasswordFormatException extends RuntimeException {
    public PasswordFormatException() {
        super("Password not valid");
    }
}
