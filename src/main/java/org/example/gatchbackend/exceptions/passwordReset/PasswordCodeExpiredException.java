package org.example.gatchbackend.exceptions.passwordReset;

public class PasswordCodeExpiredException extends RuntimeException {
    public PasswordCodeExpiredException() {
        super("The password code has already expired.");
    }
}
