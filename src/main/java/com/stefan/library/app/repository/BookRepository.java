package com.stefan.library.app.repository;

import com.stefan.library.app.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByBookTitleAndBookAuthor(String bookTitle, String bookAuthor);
    List<Book> findByBookTitleContainingIgnoreCase(String bookTitle);
    List<Book> findByBookAuthorContainingIgnoreCase(String bookAuthor);

}
