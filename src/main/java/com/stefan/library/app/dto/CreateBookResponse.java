package com.stefan.library.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookResponse {
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
}
