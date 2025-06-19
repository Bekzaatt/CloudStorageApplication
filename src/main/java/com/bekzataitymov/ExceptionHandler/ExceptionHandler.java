package com.bekzataitymov.ExceptionHandler;

import com.bekzataitymov.ExceptionHandler.CustomException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> UnknownException(Exception ex){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<Map<String, String>> thereIsNoSuchUserException(InvalidCredentials ex){
        return new ResponseEntity(Collections.singletonMap("message", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationException(MethodArgumentNotValidException ex){
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((objectError) ->{
                    String name = ((FieldError) objectError).getField();;
                    String msg = objectError.getDefaultMessage();
                    map.put(name, msg);
                });
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> unauthorizedException(UnauthorizedException ex){
        return new ResponseEntity(Collections.singletonMap("message", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PathInvalidException.class)
    public ResponseEntity<Map<String, String>> pathInvalidException(PathInvalidException ex){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExistsAlreadyException.class)
    public ResponseEntity<Map<String, String>> existsAlreadyException(ExistsAlreadyException ex){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFoundException(NotFoundException ex){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
