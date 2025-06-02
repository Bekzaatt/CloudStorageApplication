package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Folder;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Service.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Working with directories")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @Operation(description = "This method gets us a files inside a directory",
            summary = "Getting the files from directories",
            parameters = @Parameter(name = "path", description = "Path to directory", example = "/files/tests/",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = Folder.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Path not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping("/directory")
    public ResponseEntity<List<Resource>> getDirectory(@RequestParam String path) throws Exception {
        return new ResponseEntity<>(directoryService.getDirectory(path), HttpStatus.OK);
    }

    @Operation(description = "This method creates a directory", summary = "Creating the directory",
            parameters = @Parameter(name = "path", description = "Path to directory", example = "/files/tests/",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Folder.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Directory already exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Root path not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping("/directory")
    public ResponseEntity<String> saveDirectory(@RequestParam String path) throws Exception {
        return new ResponseEntity<>(directoryService.saveDirectory(path), HttpStatus.CREATED);
    }
}
