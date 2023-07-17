package com.stefan.library.app.controller;

import com.stefan.library.app.dto.ErrorResponseDTO;
import com.stefan.library.app.dto.LoginResponseDTO;
import com.stefan.library.app.dto.RegistrationDTO;
import com.stefan.library.app.dto.RegistrationResponseDTO;
import com.stefan.library.app.models.*;
import com.stefan.library.app.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body) {
        RegistrationRequest registrationRequest = new RegistrationRequest(body.getUsername(), body.getPassword());
        ValidationResult validationResult = registrationRequest.validate();

        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }

        ApplicationUser registeredUser = authenticationService.registerUser(body.getUsername(), body.getPassword());
        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO(
                registeredUser.getUserId(),
                registeredUser.getUsername(),
                "Successfully registered"
        );
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegistrationDTO body) {
        RegistrationRequest registrationRequest = new RegistrationRequest(body.getUsername(), body.getPassword());
        ValidationResult validationResult = registrationRequest.validate();
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        LoginResponseDTO response = authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (response.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO("Incorrect username or password"));
        }
        return ResponseEntity.ok(response);
    }

}
