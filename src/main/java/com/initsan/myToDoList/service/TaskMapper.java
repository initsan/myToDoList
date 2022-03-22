package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);

    @Mapping(target = "rmv", defaultValue = "0", ignore = true)
    Task taskDtoToTask(TaskDto dto);

}
