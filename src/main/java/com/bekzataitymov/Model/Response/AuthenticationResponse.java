package com.bekzataitymov.Model.Response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String username;
    private String password;

    public AuthenticationResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthenticationResponse(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
