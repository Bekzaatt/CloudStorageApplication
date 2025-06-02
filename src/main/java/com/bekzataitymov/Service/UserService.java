package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String findUserBySessions(HttpSession httpSession){
        String username = (String)httpSession.getAttribute("username");
        System.out.println("Username: " + httpSession.getAttribute("username"));
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        return username;

    }
}
