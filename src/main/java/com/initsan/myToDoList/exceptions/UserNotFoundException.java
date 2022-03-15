package com.initsan.myToDoList.exceptions;

public class UserNotFoundException extends RuntimeException{

    private final String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }

    public UserNotFoundException() {
        this.message = "User not found";
    }

    public String getMessage() {
        return message;
    }

}
