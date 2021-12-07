package com.switchfully.projects.eurder.domain.exception;

public class InvalidEmailStructureException extends RuntimeException {

    public InvalidEmailStructureException(String message) {
        super(message);
    }
}
