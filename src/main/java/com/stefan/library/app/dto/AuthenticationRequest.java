package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {
    @NotBlank(message = "Username cannot be empty")
    @Size(max = 15, message = "Maximum username length is 15 characters")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password length should be at least 8 characters")
    private String password;
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

