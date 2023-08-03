package com.stefan.library.app.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Integer bookId;
    @Column
    private String bookTitle;
    @Column
    private String bookAuthor;

}
