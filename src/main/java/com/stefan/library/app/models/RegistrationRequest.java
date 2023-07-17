package com.stefan.library.app.models;

public class RegistrationRequest {
    private String username;
    private String password;
    public RegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ValidationResult validate() {
        if (username.isEmpty()) {
            return ValidationResult.invalid("Username cannot be empty");
        }

        if (password.isEmpty()) {
            return ValidationResult.invalid("Password cannot be empty");
        }

        if (username.length() > 15) {
            return ValidationResult.invalid("Maximum username length is 15 characters");
        }

        if (password.length() < 8) {
            return ValidationResult.invalid("Password length should be at least 8 characters");
        }

        return ValidationResult.valid();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

