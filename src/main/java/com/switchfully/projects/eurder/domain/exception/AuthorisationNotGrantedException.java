package com.switchfully.projects.eurder.domain.exception;

public class AuthorisationNotGrantedException extends RuntimeException {
    public AuthorisationNotGrantedException(String message) {
        super(message);
    }
}
