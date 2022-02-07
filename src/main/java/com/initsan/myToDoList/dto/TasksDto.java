package com.initsan.myToDoList.dto;

import com.initsan.myToDoList.dictionary.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksDto {

    private Integer id;
    private Status status;
    private String title;
    private String description;
    private LocalDateTime createDate;
    private int rmv;

}
