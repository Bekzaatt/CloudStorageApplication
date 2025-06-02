package com.bekzataitymov.ExceptionHandler.CustomException;

public class InvalidCredentials extends RuntimeException{
    private String message;

    public InvalidCredentials(String message) {
        super(message);
    }
}
