package com.bekzataitymov.ExceptionHandler.CustomException;

public class UnauthorizedException extends RuntimeException{
    private String message;

    public UnauthorizedException(String message) {
        super(message);
    }
}
