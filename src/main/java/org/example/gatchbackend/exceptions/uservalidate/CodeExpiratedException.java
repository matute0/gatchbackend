package org.example.gatchbackend.exceptions.uservalidate;

public class CodeExpiratedException extends RuntimeException {
    public CodeExpiratedException() {
        super("This code has already expired");
    }
}
