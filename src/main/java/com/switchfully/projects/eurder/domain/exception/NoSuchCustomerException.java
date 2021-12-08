package com.switchfully.projects.eurder.domain.exception;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String message) {
        super(message);
    }
}
