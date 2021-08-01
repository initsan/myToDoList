package com.initsan.myToDoList.service;

import com.initsan.myToDoList.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.exceptions.ValidationException;

import java.util.List;

public interface TasksService {
    TasksDto addTask(TasksDto tasksDto) throws ValidationException;
    void removeTask(Integer taskId);
    TasksDto changeStatus(Integer taskId, Status status);
    TasksDto findByTitle(String title);
    List<TasksDto> getAllTasks();
}
