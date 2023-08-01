package com.stefan.library.app.dto;

import com.stefan.library.app.models.ApplicationUser;

public class LoginResponse {
    private ApplicationUser userName;
    private String accessToken;
    public LoginResponse(ApplicationUser userName, String accessToken) {
        this.userName = userName;
        this.accessToken = accessToken;
    }
    public ApplicationUser getUser() {
        return userName;
    }
    public void setUser(String user) {
        this.userName = userName;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
