package com.bekzataitymov.ExceptionHandler.CustomException;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message) {
        super(message);
    }
}