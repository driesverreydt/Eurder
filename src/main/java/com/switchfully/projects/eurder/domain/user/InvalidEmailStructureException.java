package com.switchfully.projects.eurder.domain.user;

public class InvalidEmailStructureException extends RuntimeException {

    public InvalidEmailStructureException(String message) {
        super(message);
    }
}
