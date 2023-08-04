package com.stefan.library.app.services;

import com.stefan.library.app.dto.UserLibraryRequest;
import com.stefan.library.app.dto.UserLibraryResponse;
import com.stefan.library.app.exception.DuplicateResourceException;
import com.stefan.library.app.exception.ResourceNotFoundException;
import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.models.UserLibrary;
import com.stefan.library.app.repository.BookRepository;
import com.stefan.library.app.repository.UserLibraryRepository;
import com.stefan.library.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class UserLibraryService {
    private final UserLibraryRepository userLibraryRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    public UserLibraryResponse addUserLibraryBook(Integer userId, Integer bookId, UserLibraryRequest userLibraryRequest) {
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book not found"));
        UserLibrary existingLibrary = userLibraryRepository.findByUserAndBook(user, book);
        if (existingLibrary == null) {
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
        throw new DuplicateResourceException("Book is already in your library");
    }
    public UserLibraryResponse updateUserLibraryBook(Integer userId, Integer bookId, UserLibraryRequest userLibraryRequest) {
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book not found"));

        UserLibrary userLibrary = userLibraryRepository.findByUserAndBook(user, book);
        if (userLibrary == null) {
            throw new ResourceNotFoundException("Book not found in the User library");
        }
        userLibrary.setStatus(userLibraryRequest.getStatus());
        userLibrary.setRating(userLibraryRequest.getRating());
        UserLibrary updatedUserLibrary = userLibraryRepository.save(userLibrary);

        UserLibraryResponse response = new UserLibraryResponse();
        response.setUserId(userId);
        response.setBookId(bookId);
        response.setStatus(updatedUserLibrary.getStatus());
        response.setRating(updatedUserLibrary.getRating());

        return response;
    }
    public void removeUserLibraryBook(Integer userId, Integer bookId) {
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book not found"));
        UserLibrary userLibrary = userLibraryRepository.findByUserAndBook(user, book);
        if (userLibrary == null) {
            throw new ResourceNotFoundException("Book not found in the User library");
        }
        userLibraryRepository.delete(userLibrary);
    }
    public List<UserLibrary> getUserLibrary(Integer userId) {
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        return userLibraryRepository.findByUser(user);
    }
    public List<UserLibrary> getAllUserLibraries() {
        List<UserLibrary> userLibraries = userLibraryRepository.findAll();
        if (userLibraries.isEmpty()) {
            throw new ResourceNotFoundException("No user libraries found");
        }
        return userLibraries;
    }
}

