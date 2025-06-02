package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Model.TYPE;
import com.bekzataitymov.Service.ResourceService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Working with resource")
public class ResourceController {
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService){
        this.resourceService = resourceService;
    }

    @Operation(description = "This method gets us a resource", summary = "Getting the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
            required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Resource.class))
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
    @GetMapping("/resource")
    public ResponseEntity<String> getResource(@RequestParam String path) throws Exception {
        return new ResponseEntity<>(resourceService.getResource(path), HttpStatus.OK);
    }

    @Operation(description = "This method deletes a resource", summary = "Deleting the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content ={
                    @Content(mediaType = "application/json") }),
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
    @DeleteMapping("/resource")
    public ResponseEntity<Void> deleteResource(@RequestParam String path) throws Exception{
        resourceService.deleteResource(path);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "This method downloads a resource", summary = "Downloading the resource",
            parameters = @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/octet-stream") }),
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
    @GetMapping("/resource/download")
    public ResponseEntity<Void> downloadResource(@RequestParam String path) throws Exception{
        resourceService.downloadResource(path);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "This method moves a resource", summary = "Moving the resource",
            parameters = {@Parameter(name = "from", description = "Path of a file that needs to be moved",
                    example = "/tests/test1.txt", required = true),
                    @Parameter(name = "to", description = "Path of a file where we need to move",
                            example = "/files/test1.txt", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Resource.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid path", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Path not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Path where we need to send a file is exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })

    })
    @GetMapping("/resource/move")
    public ResponseEntity<String> moveResource(@RequestParam String from,
                                               @RequestParam String to) throws Exception{
        return new ResponseEntity<>(resourceService.moveResource(from, to), HttpStatus.OK);
    }

    @Operation(description = "This method searches a resource", summary = "Searching the resource",
            parameters = @Parameter(name = "query", description = "Path to file", example = "/tests/test1.txt",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content ={
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = Resource.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping("/resource/search")
    public ResponseEntity<List> searchResource(@RequestParam String query) throws Exception{
        return new ResponseEntity<>(resourceService.searchResource(query), HttpStatus.OK);
    }

    @Operation(description = "This method uploads a resource", summary = "Uploading the resource",
            parameters = {
                    @Parameter(name = "path", description = "Path to file", example = "/tests/test1.txt",
                            required = true),
                    @Parameter(name = "file", description = "MultiPart file", required = true)

    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content ={
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = Resource.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid or empty path", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Resource already exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping("/resource")
    public ResponseEntity<List> uploadResource(@RequestParam("path") String path,
                                                 @RequestParam("file") MultipartFile file) throws Exception{
        return new ResponseEntity<>(resourceService.uploadResource(path, file), HttpStatus.CREATED);

    }

}
