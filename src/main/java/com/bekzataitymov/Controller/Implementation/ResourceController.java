package com.bekzataitymov.Controller.Implementation;

import com.bekzataitymov.Controller.Interface.ResourceControllerInterface;
import com.bekzataitymov.Service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceController implements ResourceControllerInterface {
    private final ResourceService resourceService;
    private final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    public ResourceController(ResourceService resourceService){
        this.resourceService = resourceService;
    }

    @GetMapping("/resource")
    public ResponseEntity<String> getResource(@RequestParam String path){
        try {
            return new ResponseEntity<>(resourceService.getResource(path), HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Minio error with creating the object.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }
    }

    @DeleteMapping("/resource")
    public ResponseEntity<String> deleteResource(@RequestParam String path){
        try {
            resourceService.deleteResource(path);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with deleting the resource.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }

    }

    @GetMapping("/resource/download")
    public ResponseEntity<String> downloadResource(@RequestParam String path){
        try {
            resourceService.downloadResource(path);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with downloading the resource.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }
    }

    @GetMapping("/resource/move")
    public ResponseEntity<String> moveResource(@RequestParam String from,
                                               @RequestParam String to){
        try {
            return new ResponseEntity<>(resourceService.moveResource(from, to), HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with moving resources.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }
    }

    @GetMapping("/resource/search")
    public ResponseEntity<List> searchResource(@RequestParam String query){
        try {
            return new ResponseEntity<>(resourceService.searchResource(query), HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with searching the resources.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(Collections.singletonList(msg + " " + e));
        }
    }

    @PostMapping("/resource")
    public ResponseEntity<List> uploadResource(@RequestParam("path") String path,
                                                 @RequestParam("file") MultipartFile[] files){
        try {
            return new ResponseEntity<>(resourceService.uploadResource(path, files), HttpStatus.CREATED);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with moving resources.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(Collections.singletonList(msg + " " + e));
        }
    }

    @PostMapping("/createResource")
    public ResponseEntity<String> createResource(@RequestParam("path") String path){
        try {
            return new ResponseEntity<>(resourceService.saveResource(path), HttpStatus.CREATED);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with saving the resource.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }
    }
}
