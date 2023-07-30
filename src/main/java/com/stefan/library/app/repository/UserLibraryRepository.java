package com.stefan.library.app.repository;

import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.Book;
import com.stefan.library.app.models.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLibraryRepository extends JpaRepository<UserLibrary, Integer> {
    List<UserLibrary> findByUser(ApplicationUser userId);

    UserLibrary findByUserAndBook(ApplicationUser user, Book book);
}