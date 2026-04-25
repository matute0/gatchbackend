package org.example.gatchbackend.exceptions.uservalidate;

public class CodeNotFoundException extends RuntimeException {
    public CodeNotFoundException() {
        super("Code not found.");
    }
}
