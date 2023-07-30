package com.stefan.library.app.services;

import com.stefan.library.app.dto.UserLibraryRequest;
import com.stefan.library.app.dto.UserLibraryResponse;
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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
public class UserLibraryService {
    private final UserLibraryRepository userLibraryRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserLibraryService(UserLibraryRepository userLibraryRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.userLibraryRepository = userLibraryRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    public UserLibraryResponse addUserLibraryBook(Integer userId, Integer bookId, UserLibraryRequest userLibraryRequest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserLibraryRequest>> violations = validator.validate(userLibraryRequest);

        ApplicationUser user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (!user.getUserId().equals(getAuthenticatedUserId())) {
            throw new AuthenticationServiceException("Unauthorized");
        }
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserLibraryRequest> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ValidationException(errorMessages);
        }
        if (book == null) {
            throw new ValidationException(Collections.singletonList("Book not found"));
        }
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
        if (!user.getUserId().equals(getAuthenticatedUserId())) {
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
        if (!user.getUserId().equals(getAuthenticatedUserId())) {
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
    private Integer getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthentication.getToken();
            Long userIdLong = jwt.getClaim("user_id");
            return userIdLong.intValue();
        } else {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
    }
}

