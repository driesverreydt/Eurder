package com.switchfully.projects.eurder.domain.exception;

public class InvalidOrderInformationException extends RuntimeException {
    public InvalidOrderInformationException(String message) {
        super(message);
    }
}
