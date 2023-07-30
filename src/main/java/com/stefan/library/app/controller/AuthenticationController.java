package com.stefan.library.app.controller;

import com.stefan.library.app.dto.*;
import com.stefan.library.app.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest request) {
        ValidationResult validationResult = request.validate();
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        RegistrationResponse registeredUser = authenticationService.registerUser(request);
        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO(
                registeredUser.getUserId(),
                registeredUser.getUsername(),
                "Successfully registered"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest request) {
        try {
            LoginResponseDTO response = authenticationService.loginUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
