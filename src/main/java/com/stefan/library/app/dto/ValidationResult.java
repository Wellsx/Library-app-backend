package com.stefan.library.app.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationResult {
    private final boolean valid;
    private final String message;
    public boolean isValid() {
        return valid;
    }
    public String getMessage() {
        return message;
    }
    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }
    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, message);
    }
}
