package com.bekzataitymov.Service;

import com.bekzataitymov.ExceptionHandler.CustomException.ExistsAlreadyException;
import com.bekzataitymov.ExceptionHandler.CustomException.InvalidCredentials;
import com.bekzataitymov.ExceptionHandler.CustomException.NotFoundException;
import com.bekzataitymov.ExceptionHandler.CustomException.UnauthorizedException;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.AuthenticationResponse;
import com.bekzataitymov.Model.Role;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.RoleRepository;
import com.bekzataitymov.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        this.roleRepository = roleRepository;

    }

    public AuthenticationResponse save(AuthenticationRequest request, HttpServletRequest req){

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        Optional<Role> userRole = roleRepository.findByName("USER");
        if(userRole.isEmpty()){
            throw new NotFoundException("Role not found.");
        }
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if(optionalUser.isPresent()){
            throw new ExistsAlreadyException("Username is busy");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.getRoles().add(userRole.get());
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("username", user.getUsername());
        userRepository.save(user);
        return new AuthenticationResponse(user.getUsername(), user.getPassword());
    }

    public AuthenticationResponse loadUserByUsername(AuthenticationRequest user, HttpServletRequest req){
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        if(optionalUser.isEmpty()){
            throw new InvalidCredentials("User not found");
        }
        if(!bCryptPasswordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())){
            throw new InvalidCredentials("Invalid password");
        }

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("username", optionalUser.get().getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(optionalUser.get().getUsername(),
                optionalUser.get().getPassword());
        return authenticationResponse;
    }

    public void logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession(false);
        String username = (String) httpSession.getAttribute("username");
        if(username == null || username.isEmpty()){
            throw new UnauthorizedException("User's not authorized");
        }
        httpSession.invalidate();
    }

}
