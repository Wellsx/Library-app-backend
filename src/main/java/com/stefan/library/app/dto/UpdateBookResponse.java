package com.stefan.library.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookResponse {
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
}

