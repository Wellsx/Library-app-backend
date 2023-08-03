package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotBlank(message = "Old Password must not be blank")
    private String oldPassword;
    @NotBlank(message = "New Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;
}
