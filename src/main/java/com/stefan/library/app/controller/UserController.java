package com.stefan.library.app.controller;

import com.stefan.library.app.dto.ChangePasswordRequest;
import com.stefan.library.app.services.AuthenticationProvider;
import com.stefan.library.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    public UserController(UserService userService, AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }
    @GetMapping("/")
    public String helloUserController() {
        return "User access level";
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest request)
            throws AuthenticationException {
        Integer userId = authenticationProvider.getAuthenticatedUserId();

        userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());

        return ResponseEntity.ok("Password changed successfully");
    }
}
