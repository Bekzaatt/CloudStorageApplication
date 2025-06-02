package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.*;
import com.bekzataitymov.Model.Folder;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.TYPE;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class DirectoryService {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private MinIOService minIOService;
    @Autowired
    private UserRepository userRepository;
    public List<Resource> getDirectory(String path) throws Exception{
        validation(path);
        return resourceService.searchResource(path);
    }

    public String saveDirectory(String path) throws Exception {
        validation(path);
        String finalPath = resourceService.getPath(path);
        if(minIOService.ifObjectsExists(finalPath)){
            throw new ThereIsSuchResourceException("There is already such resource");
        }
        resourceService.saveResource(path);
        Folder folder = getFolderTemplate(path);
        return new ObjectMapper().writeValueAsString(folder);
    }

    private void validation(String path){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("username");
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        Optional<User> user = userRepository.findByUsername(username);

        String finalPath = "user-"+user.get().getId()+"-files" + "/" + path;
        try {
            Paths.get(finalPath);
        }catch (InvalidPathException | NullPointerException ex){
            throw new PathInvalidException("Path is invalid");
        }
        String temp = finalPath.substring(0, finalPath.lastIndexOf("/"));
        String objectName = temp.substring(0, temp.lastIndexOf("/") + 1);
        if(objectName.charAt(objectName.length() - 1) == '/' && !minIOService.ifObjectsExists(objectName)){
            throw new DirectoryNotFoundException("Directory not found");
        }
    }

    private Folder getFolderTemplate(String path) {
        String temp = path.substring(0, path.lastIndexOf("/"));
        String name = temp.substring(temp.lastIndexOf("/") + 1);
        String pathToFile = temp.substring(0, temp.lastIndexOf("/") + 1);
        return new Folder(pathToFile, name, TYPE.DIRECTORY);
    }
}
