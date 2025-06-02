package com.bekzataitymov.ExceptionHandler.CustomException;

import java.util.Map;

public class ResourceNotFoundException extends RuntimeException {
    private String message;
    public ResourceNotFoundException(String message){
        super(message);
    }
}
