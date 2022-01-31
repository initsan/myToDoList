package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TasksConverter {

    public Task taskDtoToTask(TasksDto tasksDto) {

        return Task.builder()
                .id(tasksDto.getId())
                .status(tasksDto.getStatus())
                .title(tasksDto.getTitle())
                .description(tasksDto.getDescription())
                .createDate(tasksDto.getCreateDate())
                .rmv(tasksDto.getRmv())
                .build();

    }

    public TasksDto taskToTaskDto(Task task) {

        return TasksDto.builder()
                .id(task.getId())
                .status(task.getStatus())
                .status(task.getStatus())
                .title(task.getTitle())
                .description(task.getDescription())
                .createDate(task.getCreateDate())
                .rmv(task.getRmv())
                .build();

    }

}
