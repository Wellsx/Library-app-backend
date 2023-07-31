package com.stefan.library.app.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserLibraryRequest {
    private Integer userId;
    private Integer bookId;
    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(reading|plan-to-read|completed)$", message = "Invalid status. Allowed values are 'reading', 'plan-to-read', or 'completed'")
    private String status;
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be greater than 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be less than 10")
    private Double rating;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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
