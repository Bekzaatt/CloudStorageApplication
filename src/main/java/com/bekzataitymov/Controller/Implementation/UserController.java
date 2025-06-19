package com.bekzataitymov.Controller.Implementation;

import com.bekzataitymov.Controller.Interface.UserControllerInterface;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Response.ErrorResponse;
import com.bekzataitymov.Model.Response.UsernameResponse;
import com.bekzataitymov.Service.AuthenticationService;
import com.bekzataitymov.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController implements UserControllerInterface {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(HttpSession httpSession){
        String username = userService.findUserBySessions(httpSession);
        return new ResponseEntity<>(Collections
                .singletonMap("username", username), HttpStatus.OK);
    }
}
