package com.stefan.library.app.controller;

import com.stefan.library.app.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.ErrorResponseDTO;
import com.stefan.library.app.models.LoginResponseDTO;
import com.stefan.library.app.models.RegistrationDTO;
import com.stefan.library.app.models.ValidationResult;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body) {

        ValidationResult validationResult = ValidationResult.validateRegistrationForm(body);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        // existing user error handling
        if (authenticationService.usernameExists(body.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Username is already taken"));
        }

        ApplicationUser registeredUser = authenticationService.registerUser(body.getUsername(), body.getPassword());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegistrationDTO body) {
        ValidationResult validationResult = ValidationResult.validateLoginForm(body);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        /*
         * if (body.getUsername() == null || body.getUsername().isEmpty()) {
         * return ResponseEntity.badRequest().body(new
         * ErrorResponseDTO("Username can't be null"));
         * }
         * if (body.getPassword() == null || body.getPassword().isEmpty()) {
         * return ResponseEntity.badRequest().body(new
         * ErrorResponseDTO("Password can't be null"));
         * }
         */
        LoginResponseDTO response = authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (response.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO("Incorrect username or password"));
        }
        return ResponseEntity.ok(response);
    }

}
