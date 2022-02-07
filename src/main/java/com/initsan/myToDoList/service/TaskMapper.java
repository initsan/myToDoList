package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TasksDto taskToTaskDto(Task task);

    Task taskDtoToTask(TasksDto dto);

}
