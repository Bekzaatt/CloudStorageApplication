package com.bekzataitymov.ExceptionHandler.CustomException;

public class ThereIsSuchUserException extends RuntimeException {
    private String message;

    public ThereIsSuchUserException(String message) {
        super(message);
    }
}
