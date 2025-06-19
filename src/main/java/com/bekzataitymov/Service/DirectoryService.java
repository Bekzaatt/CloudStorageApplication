package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.*;
import com.bekzataitymov.Model.Folder;
import com.bekzataitymov.Model.Resource;
import com.bekzataitymov.Model.Type;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.UserRepository;
import com.bekzataitymov.Utils.ValidationUtil;
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
    private ValidationUtil validation;
    public List<Resource> getDirectory(String path) throws Exception{
        validation.validationForDirectory(path);
        return resourceService.searchResource(path);
    }

    public String saveDirectory(String path) throws Exception {
        validation.validationForDirectory(path);
        String finalPath = resourceService.getPath(path);
        if(minIOService.isObjectsExists(finalPath)){
            throw new ExistsAlreadyException("There is already such resource");
        }
        minIOService.createObject(finalPath);
        Folder folder = getFolderTemplate(path);
        return new ObjectMapper().writeValueAsString(folder);
    }

    private Folder getFolderTemplate(String path) {
        String temp = path.substring(0, path.lastIndexOf("/"));
        String name = temp.substring(temp.lastIndexOf("/") + 1);
        String pathToFile = temp.substring(0, temp.lastIndexOf("/") + 1);
        return new Folder(pathToFile, name, Type.DIRECTORY);
    }
}
