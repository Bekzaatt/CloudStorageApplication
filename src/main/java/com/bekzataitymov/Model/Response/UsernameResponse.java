package com.bekzataitymov.Model.Response;

import lombok.Data;

@Data
public class UsernameResponse {
    private String username;

    public UsernameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
