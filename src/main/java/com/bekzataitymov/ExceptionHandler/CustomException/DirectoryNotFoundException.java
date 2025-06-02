package com.bekzataitymov.ExceptionHandler.CustomException;

public class DirectoryNotFoundException extends RuntimeException {
    private String message;

    public DirectoryNotFoundException(String message) {
        super(message);
    }
}
