package com.stefan.library.app.dto;

import com.stefan.library.app.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private ApplicationUser userName;
    private String accessToken;
}
