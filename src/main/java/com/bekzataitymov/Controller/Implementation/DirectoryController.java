package com.bekzataitymov.Controller.Implementation;

import com.bekzataitymov.Controller.Interface.DirectoryControllerInterface;
import com.bekzataitymov.Service.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DirectoryController implements DirectoryControllerInterface {
    private final DirectoryService directoryService;
    private final Logger logger = LoggerFactory.getLogger(DirectoryController.class);

    @Autowired
    public DirectoryController(DirectoryService directoryService){
        this.directoryService = directoryService;
    }

    @Override
    @GetMapping("/directory")
    public ResponseEntity<?> getDirectory(@RequestParam String path){
        try {
            return new ResponseEntity<>(directoryService.getDirectory(path), HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with getting the directory.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);
        }
    }

    @Override
    @PostMapping("/directory")
    public ResponseEntity<String> saveDirectory(@RequestParam String path){
        try {
            return new ResponseEntity<>(directoryService.saveDirectory(path), HttpStatus.CREATED);
        } catch (Exception e) {
            String msg = "Minio error with creating the object or with saving the directory.";
            logger.error("{} : {}", msg, e.getMessage(), e);
            return ResponseEntity.badRequest().body(msg + " " + e);        }
    }
}
