package com.stefan.library.app.services;

import com.stefan.library.app.dto.UserLibraryRequest;
import com.stefan.library.app.dto.UserLibraryResponse;
import com.stefan.library.app.exception.BookNotFoundException;
import com.stefan.library.app.exception.ValidationException;
import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.models.UserLibrary;
import com.stefan.library.app.repository.BookRepository;
import com.stefan.library.app.repository.UserLibraryRepository;
import com.stefan.library.app.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserLibraryService {
    private final UserLibraryRepository userLibraryRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthenticationProvider authenticationProvider;

    public UserLibraryService(UserLibraryRepository userLibraryRepository, UserRepository userRepository,
                              BookRepository bookRepository,
                              AuthenticationProvider authenticationProvider) {
        this.userLibraryRepository = userLibraryRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.authenticationProvider = authenticationProvider;
    }
    public UserLibraryResponse addUserLibraryBook(Integer userId, Integer bookId, UserLibraryRequest userLibraryRequest)
            throws BookNotFoundException{
        ApplicationUser user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow(new BookNotFoundException());
            UserLibrary userLibrary = new UserLibrary();
            userLibrary.setUser(user);
            userLibrary.setBook(book);
            userLibrary.setStatus(userLibraryRequest.getStatus());
            userLibrary.setRating(userLibraryRequest.getRating());
            UserLibrary newLibrary = userLibraryRepository.save(userLibrary);

            UserLibraryResponse response = new UserLibraryResponse();
            response.setUserId(userId);
            response.setBookId(bookId);
            response.setStatus(newLibrary.getStatus());
            response.setRating(newLibrary.getRating());

            return response;
    }
    public ResponseEntity<UserLibrary> updateUserLibraryBook(Integer userId, Integer bookId, UserLibraryRequest userLibraryRequest) {
        ApplicationUser user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return ResponseEntity.notFound().build();
        }
        UserLibrary userLibrary = userLibraryRepository.findByUserAndBook(user, book);
        if (userLibrary == null) {
            return ResponseEntity.notFound().build();
        }
        if (!user.getUserId().equals(authenticationProvider.getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserLibraryRequest>> violations = validator.validate(userLibraryRequest);

        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserLibraryRequest> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ValidationException(errorMessages);
        }
        userLibrary.setStatus(userLibraryRequest.getStatus());
        userLibrary.setRating(userLibraryRequest.getRating());
        UserLibrary updatedUserLibrary = userLibraryRepository.save(userLibrary);
        return ResponseEntity.ok(updatedUserLibrary);
    }
    public ResponseEntity<Void> removeUserLibraryBook(Integer userId, Integer bookId) {
        ApplicationUser user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return ResponseEntity.notFound().build();
        }
        UserLibrary userLibrary = userLibraryRepository.findByUserAndBook(user, book);
        if (userLibrary == null) {
            return ResponseEntity.notFound().build();
        }
        if (!user.getUserId().equals(authenticationProvider.getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userLibraryRepository.delete(userLibrary);
        return ResponseEntity.noContent().build();
    }
    public List<UserLibrary> getUserLibrary(Integer userId) {
        ApplicationUser user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ValidationException(Collections.singletonList("User not found"));
        }
        return userLibraryRepository.findByUser(user);
    }
    public List<UserLibrary> getAllUserLibraries() {
        List<UserLibrary> userLibraries = userLibraryRepository.findAll();
        if (userLibraries.isEmpty()) {
            throw new ValidationException(Collections.singletonList("No user libraries found"));
        }
        return userLibraries;
    }
}

