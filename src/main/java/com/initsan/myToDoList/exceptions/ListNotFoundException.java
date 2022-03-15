package com.initsan.myToDoList.exceptions;

public class ListNotFoundException extends RuntimeException{

    private final String message;

    public ListNotFoundException(String message) {
        this.message = message;
    }

    public ListNotFoundException() {
        this.message = "Task not found";
    }

    public String getMessage() {
        return message;
    }

}
