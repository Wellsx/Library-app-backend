package com.stefan.library.app.models;

public class ValidationResult {
    private final boolean valid;
    private final String message;

    private ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

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

    public ValidationResult(String message) {
        this.valid = false;
        this.message = message;
    }

    public static ValidationResult validateRegistrationForm(RegistrationDTO body) {
        if (body.getUsername().isEmpty()) {
            return new ValidationResult("Username cannot be empty");
        }

        if (body.getPassword().isEmpty()) {
            return new ValidationResult("Password cannot be empty");
        }

        if (body.getUsername().length() > 15) {
            return new ValidationResult("Username length exceeds the maximum allowed");
        }

        if (body.getPassword().length() < 8) {
            return new ValidationResult("Password length should be at least 8 characters");
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateLoginForm(RegistrationDTO body) {
        if (body.getUsername().isEmpty()) {
            return new ValidationResult("Username cannot be empty");
        }

        if (body.getPassword().isEmpty()) {
            return new ValidationResult("Password cannot be empty");
        }

        return ValidationResult.valid();
    }
}
