package com.initsan.myToDoList.service;

import com.initsan.myToDoList.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Tasks;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class DefaultTasksService implements TasksService{

    private final TasksRepository repository;
    private final TasksConverter converter;

    @Override
    public TasksDto addTask(TasksDto tasksDto) throws ValidationException {
        validateTaskDto(tasksDto);
        tasksDto.setStatus(Status.PROCESSING);
        tasksDto.setCreateDate(LocalDateTime.now());
        tasksDto.setRmv(0);
        Tasks newTask = repository.save(converter.fromTasksDtoToTasks(tasksDto));
        return converter.fromTasksToTasksDto(newTask);
    }

    @Override
    public void removeTask(Integer taskId) {
        var currentTask = repository.findById(taskId);
        if (currentTask.isPresent()) {
            currentTask.get().setRmv(1);
            repository.save(currentTask.get());
        } else {
            throw new NullPointerException(String.format("Task %s not found", taskId));
        }
    }

    @Override
    public TasksDto changeStatus(Status status) {
        //TODO change Status method
        return null;
    }

    @Override
    public TasksDto findByTitle(String title) {
        Tasks task = repository.findByTitle(title);
        if (!isNull(task)) {
            return converter.fromTasksToTasksDto(task);
        }
        return null;
    }

    @Override
    public List<TasksDto> getAllTasks() {
        return repository.findAll()
                .stream()
                .filter(tasks -> tasks.getRmv() != 1)
                .map(converter::fromTasksToTasksDto)
                .collect(Collectors.toList());
    }

    private void validateTaskDto(TasksDto tasksDto) throws ValidationException {
        if (isNull(tasksDto)) {
            throw new ValidationException("Object task is null");
        }
        if (isNull(tasksDto.getTitle())) {
            throw new ValidationException("Title is empty");
        }
    }

}
