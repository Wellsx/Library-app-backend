package com.stefan.library.app.repository;

import com.stefan.library.app.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByBookTitleAndBookAuthor(String bookTitle, String bookAuthor);
}
