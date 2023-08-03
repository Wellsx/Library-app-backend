package com.stefan.library.app.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLibraryRequest {
    private Integer userId;
    private Integer bookId;
    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(reading|plan-to-read|completed)$", message = "Invalid status. Allowed values are 'reading', 'plan-to-read', or 'completed'")
    private String status;
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be greater than 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be less than 10")
    private Double rating;
}
