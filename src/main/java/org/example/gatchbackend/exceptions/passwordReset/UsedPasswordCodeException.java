package org.example.gatchbackend.exceptions.passwordReset;

public class UsedPasswordCodeException extends RuntimeException {
    public UsedPasswordCodeException() {
        super("Password code has already used.");
    }
}
