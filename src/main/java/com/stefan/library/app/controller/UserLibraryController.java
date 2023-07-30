package com.stefan.library.app.controller;

import com.stefan.library.app.dto.UserLibraryRequest;
import com.stefan.library.app.dto.UserLibraryResponse;
import com.stefan.library.app.models.UserLibrary;
import com.stefan.library.app.services.UserLibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-libraries")
@CrossOrigin("*")
public class UserLibraryController {
    private final UserLibraryService userLibraryService;

    public UserLibraryController(UserLibraryService userLibraryService) {
        this.userLibraryService = userLibraryService;
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
                                                                  @RequestBody @Validated UserLibraryRequest userLibraryRequest) {
        UserLibraryResponse response = userLibraryService.addUserLibraryBook(userId, bookId, userLibraryRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}/{bookId}")
    public ResponseEntity<UserLibrary> updateUserLibraryBook(@PathVariable Integer userId, @PathVariable Integer bookId,
                                                             @RequestBody @Validated UserLibraryRequest userLibraryRequest) {
        ResponseEntity<UserLibrary> response = userLibraryService.updateUserLibraryBook(userId, bookId, userLibraryRequest);
        return ResponseEntity.ok(response.getBody());
    }
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> removeUserLibraryBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        userLibraryService.removeUserLibraryBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }
}
