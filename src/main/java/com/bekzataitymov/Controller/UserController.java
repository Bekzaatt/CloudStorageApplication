package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Model.Response.UsernameResponse;
import com.bekzataitymov.Service.AuthenticationService;
import com.bekzataitymov.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@SecurityRequirement(name = "Authorize")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(description = "This method is getting the current user", summary = "Get the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = UsernameResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping("/user/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(HttpSession httpSession){
        String username = userService.findUserBySessions(httpSession);
        return new ResponseEntity<>(Collections
                .singletonMap("username", username), HttpStatus.OK);
    }
}
