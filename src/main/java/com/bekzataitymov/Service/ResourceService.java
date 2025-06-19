package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.*;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.Type;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.UserRepository;
import com.bekzataitymov.Utils.ValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.Result;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;


@Service
public class ResourceService {
    @Autowired
    private MinIOService minIOService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationUtil validation;

    public String getResource(String path) throws Exception{
        validation.validationForResource(path);
        Resource file = getFileTemplate(path);
        return new ObjectMapper().writeValueAsString(file);
    }

    public void deleteResource(String path) throws Exception{
        validation.validationForResource(path);
        String finalPath = getPath(path);
        minIOService.deleteObject(finalPath);
    }

    public void downloadResource(String path) throws Exception{
        validation.validationForResource(path);
        String finalPath = getPath(path);
        minIOService.downloadObject(finalPath);
    }

    public String moveResource(String from, String to) throws Exception{
        validation.validationForResource(from);
        String finalFrom = getPath(from);
        String finalTo = getPath(to);
        if(minIOService.isPathExists(finalTo)){
            throw new ExistsAlreadyException("Such resource already exists.");
        }
        minIOService.moveObject(finalFrom, finalTo);
        Resource file = getFileTemplate(to);
        return new ObjectMapper().writeValueAsString(file);
    }

    public List<Resource> searchResource(String path) throws Exception{
        validation.validationForResource(path);
        String finalPath = getPath(path);
        Iterable<Result<Item>> objects = minIOService.getAllObjects(finalPath);
        String pathToFile = path.substring(0, path.lastIndexOf("/") + 1);
        List<Resource> files = new ArrayList<>();
        Resource file = null;
        for(Result<Item> object : objects){
            Item item = object.get();
            String name = item.objectName().substring(item.objectName().lastIndexOf("/") + 1);


            if(name.isEmpty() && !item.objectName().endsWith("/")){
                file = new Resource(pathToFile, name, (byte)item.size(), Type.NULL);
                files.add(file);
            }
            else if(!name.isEmpty()){
                file = new Resource(pathToFile, name, (byte) item.size(), Type.FILE);
                files.add(file);
            }
        }
        return files;
    }

    public List<Resource> uploadResource(String objectPath, MultipartFile[] files) throws Exception{
        validation.validationForResource(objectPath);
        for(MultipartFile filename : files) {
            String path = getPath(objectPath + filename.getOriginalFilename());
            if (filename.isEmpty() || filename == null) {
                throw new InvalidCredentials("Invalid credentials.");
            }
            if (minIOService.isPathExists(path)) {
                throw new ExistsAlreadyException("File is already exists.");
            }
            String finalPath = getPath(objectPath);
            minIOService.uploadObject(finalPath, filename);
        }
        return searchResource(objectPath);
    }

    public String saveResource(String path) throws Exception {
        String finalPath = getPath(path);
        System.out.println("finalPath: " + finalPath);
        minIOService.createObject(finalPath);
        Resource file = getFileTemplate(path);
        return new ObjectMapper().writeValueAsString(file);
    }

    public String getPath(String path){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = (String)securityContext.getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("getPath: + " + path);
        String finalPath = "user-"+user.get().getId()+"-files" + "/" + path;

        return finalPath;
    }

    private Resource getFileTemplate(String path) throws Exception{
        String finalPath = getPath(path);
        String pathToFile = path.substring(0, path.lastIndexOf("/") + 1);
        String name = path.substring(path.lastIndexOf("/") + 1);
        Resource file = new Resource(pathToFile, name, minIOService.getSize(finalPath), Type.FILE);
        return file;
    }

}
