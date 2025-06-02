package com.bekzataitymov.ExceptionHandler.CustomException;

public class ThereIsSuchResourceException extends RuntimeException {
    private String message;
    public ThereIsSuchResourceException(String message) {
        super(message);
    }
}
