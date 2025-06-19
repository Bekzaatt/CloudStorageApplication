package com.bekzataitymov.Controller.Interface;

import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Model.Response.UsernameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@SecurityRequirement(name = "Authorize")
public interface UserControllerInterface {

    @Operation(description = "This method is getting the current user", summary = "Get the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = UsernameResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<Map<String, String>> getCurrentUser(HttpSession httpSession);
}
