
package com.klu.microservices.dto;

import java.util.List;

public class TokenResponse {

    private String message;
    private String username;
    private List<String> roles;

    public TokenResponse() {
    }

    public TokenResponse(String message, String username,
                         List<String> roles) {
        this.message = message;
        this.username = username;
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
