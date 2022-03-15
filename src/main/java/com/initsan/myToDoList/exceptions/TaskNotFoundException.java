package com.initsan.myToDoList.exceptions;

public class TaskNotFoundException extends RuntimeException{

    private final String message;

    public TaskNotFoundException(String message) {
        this.message = message;
    }

    public TaskNotFoundException() {
        this.message = "Task not found";
    }

    public String getMessage() {
        return message;
    }

}
