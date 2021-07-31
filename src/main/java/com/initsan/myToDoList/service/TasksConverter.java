package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Tasks;
import org.springframework.stereotype.Component;

@Component
public class TasksConverter {

    public Tasks fromTasksDtoToTasks(TasksDto tasksDto) {

        return Tasks.builder()
                .id(tasksDto.getId())
                .status(tasksDto.getStatus())
                .title(tasksDto.getTitle())
                .description(tasksDto.getDescription())
                .createDate(tasksDto.getCreateDate())
                .build();

    }

    public TasksDto fromTasksToTasksDto(Tasks tasks) {

        return TasksDto.builder()
                .id(tasks.getId())
                .status(tasks.getStatus())
                .status(tasks.getStatus())
                .title(tasks.getTitle())
                .description(tasks.getDescription())
                .createDate(tasks.getCreateDate())
                .build();

    }

}
