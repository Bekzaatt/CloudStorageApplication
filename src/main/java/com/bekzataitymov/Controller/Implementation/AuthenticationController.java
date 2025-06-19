package com.bekzataitymov.Controller.Implementation;

import com.bekzataitymov.Controller.Interface.AuthenticationControllerInterface;
import com.bekzataitymov.ExceptionHandler.CustomException.InvalidCredentials;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController implements AuthenticationControllerInterface {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody AuthenticationRequest user, HttpServletRequest request)
            throws Exception{
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(authenticationService.save(user, request)),
                HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@Valid @RequestBody AuthenticationRequest user,
                                                         HttpServletRequest request) throws Exception {
        AuthenticationResponse req = authenticationService.loadUserByUsername(user, request);
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(req), HttpStatus.OK);
    }

    @Override
    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(HttpServletRequest request){
        authenticationService.logout(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}