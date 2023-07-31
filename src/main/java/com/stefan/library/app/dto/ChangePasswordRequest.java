package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank(message = "Old Password must not be blank")
    private String oldPassword;
    @NotBlank(message = "New Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
