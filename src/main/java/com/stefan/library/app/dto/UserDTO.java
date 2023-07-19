package com.stefan.library.app.dto;

public class UserDTO {


    private Integer userId;
    private String username;

    public UserDTO() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {

        return username;
    }
}
