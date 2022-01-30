package com.initsan.myToDoList.exceptions;

public class ValidationException extends Exception {

    private final String message;

    public ValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
