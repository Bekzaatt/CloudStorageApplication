package com.bekzataitymov.Utils;

import com.bekzataitymov.ExceptionHandler.CustomException.NotFoundException;
import com.bekzataitymov.ExceptionHandler.CustomException.PathInvalidException;
import com.bekzataitymov.ExceptionHandler.CustomException.UnauthorizedException;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.UserRepository;
import com.bekzataitymov.Service.MinIOService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Optional;
@Component
public final class ValidationUtil {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MinIOService minIOService;

    public void validationForResource(String path) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("username");
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        Optional<User> user = userRepository.findByUsername(username);

        String root = "user-"+user.get().getId()+"-files/";
        if(!minIOService.isObjectsExists(root)){
            minIOService.createObject(root);
        }

        String finalPath = "user-"+user.get().getId()+"-files" + "/" + path;
        try {
            Paths.get(finalPath);
        }catch (InvalidPathException | NullPointerException ex){
            throw new PathInvalidException("Path is invalid");
        }
        if(finalPath.charAt(finalPath.length() - 1) != '/' && !minIOService.isPathExists(finalPath)){
            throw new NotFoundException("Resource not found");
        }
        if(finalPath.charAt(finalPath.length() - 1) == '/' && !minIOService.isObjectsExists(finalPath)){
            throw new NotFoundException("Directory not found");
        }
    }

    public void validationForDirectory(String path) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("username");
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        Optional<User> user = userRepository.findByUsername(username);

        String root = "user-"+user.get().getId()+"-files" + "/";
        if(!minIOService.isObjectsExists(root)){
            minIOService.createObject(root);
        }

        String finalPath = "user-"+user.get().getId()+"-files" + "/" + path;
        try {
            Paths.get(finalPath);
        }catch (InvalidPathException | NullPointerException ex){
            throw new PathInvalidException("Path is invalid");
        }
        if(finalPath.charAt(finalPath.length() - 1) != '/'){
            throw new PathInvalidException("Path is invalid");
        }

        String temp = finalPath.substring(0, finalPath.lastIndexOf("/")); // docs/files/ -> docs/files
        String objectName = temp.substring(0, temp.lastIndexOf("/") + 1); // docs/
        if(objectName.charAt(objectName.length() - 1) == '/' && !minIOService.isObjectsExists(objectName)){
            throw new NotFoundException("Directory not found");
        }
    }
}
