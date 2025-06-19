package com.bekzataitymov.ExceptionHandler.CustomException;

public class ExistsAlreadyException extends RuntimeException {
    private String message;

    public ExistsAlreadyException(String message) {
        super(message);
    }
}

