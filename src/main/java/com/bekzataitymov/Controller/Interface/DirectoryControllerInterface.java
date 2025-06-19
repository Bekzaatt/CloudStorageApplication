package com.bekzataitymov.Controller.Interface;

import com.bekzataitymov.Model.Folder;
import com.bekzataitymov.Model.Response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Working with directories")
public interface DirectoryControllerInterface {
    @Operation(description = "This method gets us a files inside a directory",
            summary = "Getting the files from directories",
            parameters = @Parameter(name = "path", description = "Path to directory", example = "/files/tests/",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = Folder.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Path not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<?> getDirectory(@RequestParam String path) throws Exception;

    @Operation(description = "This method creates a directory", summary = "Creating the directory",
            parameters = @Parameter(name = "path", description = "Path to directory", example = "/files/tests/",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = Folder.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Directory already exists", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Root path not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<String> saveDirectory(@RequestParam String path) throws Exception;
}
