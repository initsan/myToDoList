package com.initsan.myToDoList.dto;

import com.initsan.myToDoList.dictionary.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TasksDto {

    private Integer id;
    private Status status;
    private String title;
    private String description;
    private LocalDateTime createDate;
    private int rmv;

}
