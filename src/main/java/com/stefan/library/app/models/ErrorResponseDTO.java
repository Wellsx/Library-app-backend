package com.stefan.library.app.models;

public class ErrorResponseDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

}
