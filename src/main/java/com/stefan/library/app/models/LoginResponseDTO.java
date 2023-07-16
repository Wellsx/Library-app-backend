package com.stefan.library.app.models;

public class LoginResponseDTO {
    private ApplicationUser user;
    private String accessToken;

    public LoginResponseDTO(ApplicationUser user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
