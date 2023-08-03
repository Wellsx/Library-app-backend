package com.stefan.library.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLibraryResponse {
    private Integer userId;
    private Integer bookId;
    private String status;
    private Double rating;
}
