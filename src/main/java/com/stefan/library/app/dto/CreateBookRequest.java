package com.stefan.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Title field cannot be empty")
    private String bookTitle;
    @NotBlank(message = "Author field cannot be empty")
    private String bookAuthor;
}
