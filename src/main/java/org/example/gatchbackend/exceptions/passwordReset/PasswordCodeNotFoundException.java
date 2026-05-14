package org.example.gatchbackend.exceptions.passwordReset;

public class PasswordCodeNotFoundException extends RuntimeException {
    public PasswordCodeNotFoundException() {
        super("Password Code not founded");
    }
}
