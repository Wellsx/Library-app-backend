package com.stefan.library.app.services;

import com.stefan.library.app.dto.UpdateBookResponse;
import com.stefan.library.app.dto.CreateBookResponse;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.dto.UpdateBookRequest;
import com.stefan.library.app.dto.CreateBookRequest;
import com.stefan.library.app.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }
    public CreateBookResponse createBook(CreateBookRequest request) {
        Book book = new Book();
        book.setBookTitle(request.getBookTitle());
        book.setBookAuthor(request.getBookAuthor());
        Book createdBook = bookRepository.save(book);

        CreateBookResponse response = new CreateBookResponse();
        response.setBookId(createdBook.getBookId());
        response.setBookTitle(createdBook.getBookTitle());
        response.setBookAuthor(createdBook.getBookAuthor());
        return response;
    }
    public UpdateBookResponse updateBook(Integer id, UpdateBookRequest updateBookRequest) {
            Optional<Book> bookOptional = bookRepository.findById(id);
            Book book = bookOptional.get();
            book.setBookTitle(updateBookRequest.getBookTitle());
            book.setBookAuthor(updateBookRequest.getBookAuthor());

            Book updatedBook = bookRepository.save(book);
            UpdateBookResponse response = new UpdateBookResponse();
            response.setBookId(updatedBook.getBookId());
            response.setBookTitle(updatedBook.getBookTitle());
            response.setBookAuthor(updatedBook.getBookAuthor());
            return response;
    }
    public void deleteBook(Integer id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        bookOptional.ifPresent(bookRepository::delete);
    }
}

