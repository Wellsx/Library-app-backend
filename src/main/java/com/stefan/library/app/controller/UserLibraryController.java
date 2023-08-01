package com.stefan.library.app.controller;

import com.stefan.library.app.dto.UserLibraryRequest;
import com.stefan.library.app.dto.UserLibraryResponse;
import com.stefan.library.app.exception.ValidationException;
import com.stefan.library.app.models.UserLibrary;
import com.stefan.library.app.services.AuthenticationProvider;
import com.stefan.library.app.services.UserLibraryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user-libraries")
@CrossOrigin("*")
public class UserLibraryController {
    private final UserLibraryService userLibraryService;
    private final AuthenticationProvider authenticationProvider;

    public UserLibraryController(UserLibraryService userLibraryService, AuthenticationProvider authenticationProvider) {
        this.userLibraryService = userLibraryService;
        this.authenticationProvider = authenticationProvider;

    }
    @GetMapping("/")
    public List<UserLibrary> getAllUserLibraries() {
        return userLibraryService.getAllUserLibraries();
    }
    @GetMapping("/{userId}")
    public List<UserLibrary> getUserLibrary(@PathVariable Integer userId) {
        return userLibraryService.getUserLibrary(userId);
    }
    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<UserLibraryResponse> addUserLibraryBook(@PathVariable Integer userId, @PathVariable Integer bookId,
                                                                  @RequestBody @Valid UserLibraryRequest userLibraryRequest){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserLibraryRequest>> violations = validator.validate(userLibraryRequest);
        if (!userId.equals(authenticationProvider.getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserLibraryRequest> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ValidationException(errorMessages);
        }
        UserLibraryResponse response = userLibraryService.addUserLibraryBook(userId, bookId, userLibraryRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}/{bookId}")
    public ResponseEntity<UserLibraryResponse> updateUserLibraryBook(@PathVariable Integer userId, @PathVariable Integer bookId,
                                                             @RequestBody @Valid UserLibraryRequest userLibraryRequest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserLibraryRequest>> violations = validator.validate(userLibraryRequest);
        if (!userId.equals(authenticationProvider.getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserLibraryRequest> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ValidationException(errorMessages);
        }
        UserLibraryResponse response = userLibraryService.updateUserLibraryBook(userId, bookId, userLibraryRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> removeUserLibraryBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        if (!userId.equals(authenticationProvider.getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userLibraryService.removeUserLibraryBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }
}
