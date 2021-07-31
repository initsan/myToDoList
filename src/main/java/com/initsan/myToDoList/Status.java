package com.initsan.myToDoList;

import lombok.Getter;

@Getter
public enum Status {
    PROCESSING ("Processing"),
    DONE("Done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

}
