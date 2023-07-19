package com.stefan.library.app.controller;

import com.stefan.library.app.dto.BookResponseDTO;
import com.stefan.library.app.dto.ErrorResponseDTO;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.models.BookRequest;
import com.stefan.library.app.models.ValidationResult;
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
    public Optional<Book> getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/addBook")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        BookRequest bookRequest = new BookRequest(book.getBookTitle(), book.getBookAuthor());
        ValidationResult validationResult = bookRequest.validateNewBook();
        if (!validationResult.isValid()){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(validationResult.getMessage()));
        }
        Book newBook = bookService.createBook(book);
        BookResponseDTO response = new BookResponseDTO(newBook.getBookId(), newBook.getBookTitle(), newBook.getBookAuthor());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
    }
}

