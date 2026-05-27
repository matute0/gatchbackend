package org.example.gatchbackend.exceptions.auth;

public class JWTError extends RuntimeException {
    public JWTError() {
        super("JWT error");
    }
}
