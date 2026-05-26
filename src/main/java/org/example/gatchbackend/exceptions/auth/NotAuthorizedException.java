package org.example.gatchbackend.exceptions.auth;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("Not Authorized user");
    }
}
