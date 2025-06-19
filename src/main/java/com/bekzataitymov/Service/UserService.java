package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.UnauthorizedException;
import com.bekzataitymov.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String findUserBySessions(HttpSession httpSession){
        final String username = (String)httpSession.getAttribute("username");
        System.out.println("Username: " + username);
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        return username;

    }
}
