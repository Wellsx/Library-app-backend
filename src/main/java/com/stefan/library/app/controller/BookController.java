package com.stefan.library.app.controller;

import com.stefan.library.app.dto.UpdateBookResponse;
import com.stefan.library.app.dto.CreateBookResponse;
import com.stefan.library.app.dto.ErrorResponseDTO;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.dto.CreateBookRequest;
import com.stefan.library.app.dto.UpdateBookRequest;
import com.stefan.library.app.dto.ValidationResult;
import com.stefan.library.app.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBook(@RequestBody CreateBookRequest request) {
        ValidationResult validationResult = request.validateNewBook();
        if (!validationResult.isValid()){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        CreateBookResponse response = bookService.createBook(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody UpdateBookRequest request) {
        UpdateBookResponse updatedBook = bookService.updateBook(id, request);
        ValidationResult validationResult = request.validateBookUpdate();
        if (updatedBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!validationResult.isValid()){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        UpdateBookResponse updateResponse = new UpdateBookResponse();
        updateResponse.setBookId(updatedBook.getBookId());
        updateResponse.setBookTitle(updatedBook.getBookTitle());
        updateResponse.setBookAuthor(updatedBook.getBookAuthor());
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

