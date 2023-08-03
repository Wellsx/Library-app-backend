package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Title field cannot be empty")
    private String bookTitle;
    @NotBlank(message = "Author field cannot be empty")
    private String bookAuthor;

    public ValidationResult validateNewBook(){
        if (StringUtils.isBlank(bookTitle)){
            return ValidationResult.invalid("Title field can't be empty");
        }
        if (StringUtils.isBlank(bookAuthor)){
            return ValidationResult.invalid("Author field can't be empty");
        }
        return ValidationResult.valid();
    }
}
