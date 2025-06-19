package com.bekzataitymov.Controller.Interface;

import com.bekzataitymov.Model.Resource;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Working with resource")
public interface ResourceControllerInterface {

    @Operation(description = "This method gets us a resource", summary = "Getting the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = Resource.class))
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
    ResponseEntity<String> getResource(@RequestParam String path) throws Exception;

    @Operation(description = "This method deletes a resource", summary = "Deleting the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
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
    ResponseEntity<String> deleteResource(@RequestParam String path) throws Exception;

    @Operation(description = "This method downloads a resource", summary = "Downloading the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
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
    ResponseEntity<String> downloadResource(@RequestParam String path) throws Exception;

    @Operation(description = "This method moves a resource", summary = "Moving the resource",
            parameters = {@Parameter(name = "from", description = "Path of a file that needs to be moved",
                    example = "/tests/test1.txt", required = true),
                    @Parameter(name = "to", description = "Path of a file where we need to move",
                            example = "/files/test1.txt", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = Resource.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid path", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Path not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Path where we need to send a file is exists", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })

    })
    ResponseEntity<String> moveResource(@RequestParam String from, @RequestParam String to) throws Exception;

    @Operation(description = "This method searches a resource", summary = "Searching the resource",
            parameters = @Parameter(name = "query", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = Resource.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<List> searchResource(@RequestParam String query) throws Exception;

    @Operation(description = "This method uploads a resource", summary = "Uploading the resource",
            parameters = {
                    @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                            required = true),
                    @Parameter(name = "file", description = "MultiPart file", required = true)

            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = Resource.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Resource already exists", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<List> uploadResource(@RequestParam("path") String path, @RequestParam("file") MultipartFile[] files)
            throws Exception;



}
