package com.bekzataitymov.ExceptionHandler.CustomException;

public class PathInvalidException extends RuntimeException {
    private String message;

    public PathInvalidException(String message) {
        super(message);
    }
}
