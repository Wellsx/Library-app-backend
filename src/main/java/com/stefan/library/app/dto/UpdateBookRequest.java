package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    @NotBlank(message = "Title field cannot be empty")
    private String bookTitle;
    @NotBlank(message = "Author field cannot be empty")
    private String bookAuthor;
}
