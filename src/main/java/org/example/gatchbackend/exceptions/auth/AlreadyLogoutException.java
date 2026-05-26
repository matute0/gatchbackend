package org.example.gatchbackend.exceptions.auth;

public class AlreadyLogoutException extends RuntimeException {
    public AlreadyLogoutException() {
        super("This user has already log out");
    }
}
