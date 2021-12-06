package com.switchfully.projects.eurder.security;

public class AuthorisationNotGrantedException extends RuntimeException {
    public AuthorisationNotGrantedException(String message) {
        super(message);
    }
}
