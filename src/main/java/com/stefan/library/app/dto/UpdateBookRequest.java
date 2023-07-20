package com.stefan.library.app.dto;

import com.stefan.library.app.models.ValidationResult;
import org.apache.commons.lang3.StringUtils;

public class UpdateBookRequest {
    private String bookTitle;
    private String bookAuthor;
    public ValidationResult validateBookUpdate(){
        if (StringUtils.isBlank(bookTitle)){
            return ValidationResult.invalid("Title field can't be empty");
        }
        if (StringUtils.isBlank(bookAuthor)){
            return ValidationResult.invalid("Author field can't be empty");
        }

        return ValidationResult.valid();
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
