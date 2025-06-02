package com.bekzataitymov.Controller;

import com.bekzataitymov.ExceptionHandler.CustomException.InvalidCredentials;
import com.bekzataitymov.ExceptionHandler.CustomException.ThereIsSuchResourceException;
import com.bekzataitymov.ExceptionHandler.CustomException.ThereIsSuchUserException;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Model.User;
//import com.bekzataitymov.Service.JwtService;
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
@Tag(name = "Authentication")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(description = "This method is for register", summary = "Register",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credentials for register",
                    required = true, content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Username is busy", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody AuthenticationRequest user, HttpServletRequest request)
            throws Exception{
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(authenticationService.save(user, request)),
                HttpStatus.CREATED);
    }

    @Operation(description = "This method is for login", summary = "Login",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credentials for login",
                    required = true, content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "There is no such user or invalid password", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@Valid @RequestBody AuthenticationRequest user,
                                                         HttpServletRequest request) throws Exception {
        AuthenticationResponse req = authenticationService.loadUserByUsername(user, request);
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(req), HttpStatus.OK);
    }

    @Operation(description = "This method is for logout", summary = "Logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(HttpServletRequest request){
        authenticationService.logout(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}