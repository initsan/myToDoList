package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);

    Task taskDtoToTask(TaskDto dto);

}
