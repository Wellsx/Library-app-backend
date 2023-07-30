package com.stefan.library.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "user-library")
public class UserLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_library_id")
    private Integer userLibraryId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column
    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(reading|plan-to-read|completed)$", message = "Invalid status. Allowed values are 'reading', 'plan-to-read', or 'completed'")
    private String status; // reading, plan-to-read, completed
    @Column
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be greater than 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be less than 10")
    private Double rating; // decimal number from 0 to 10

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
