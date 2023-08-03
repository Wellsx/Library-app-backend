package com.stefan.library.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends Exception {
    private final List<String> errorMessages;
    public ValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
