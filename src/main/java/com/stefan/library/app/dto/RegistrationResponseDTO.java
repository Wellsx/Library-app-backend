package com.stefan.library.app.dto;

public class RegistrationResponseDTO {
    private String username;
    private Integer userId;
    private String message;
    public RegistrationResponseDTO(Integer userId, String username, String message) {
        this.username = username;
        this.userId = userId;
        this.message = message;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


