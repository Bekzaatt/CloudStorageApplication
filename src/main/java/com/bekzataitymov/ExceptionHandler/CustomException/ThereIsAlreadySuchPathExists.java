package com.bekzataitymov.ExceptionHandler.CustomException;

public class ThereIsAlreadySuchPathExists extends RuntimeException {
    private String message;
    public ThereIsAlreadySuchPathExists(String message) {
        super(message);
    }
}
