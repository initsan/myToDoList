package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Task;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class TasksService {

    private final TasksRepository repository;
    private final TaskMapper converter;

    @Autowired
    public TasksService(TasksRepository repository, TaskMapper converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public TasksDto addTask(TasksDto tasksDto) throws ValidationException {
        validateTaskDto(tasksDto);
        tasksDto.setStatus(Status.PROCESSING);
        tasksDto.setCreateDate(LocalDateTime.now());
        tasksDto.setUserId(2L);
        tasksDto.setRmv(0);
        return converter.taskToTaskDto(repository.save(converter.taskDtoToTask(tasksDto)));
    }

    public void removeTask(Long taskId) {
        var currentTask = repository.findById(taskId);
        if (currentTask.isPresent()) {
            currentTask.get().setRmv(1);
            repository.save(currentTask.get());
        } else {
            throw new NullPointerException(String.format("Task %s not found", taskId));
        }
    }

    public TasksDto changeStatus(Long taskId, Status status) {
        var currentTask = repository.findById(taskId);
        if (currentTask.isPresent()) {
            currentTask.get().setStatus(status);
            return converter.taskToTaskDto(repository.save(currentTask.get()));
        } else {
            throw new NullPointerException(String.format("Task %s not found", taskId));
        }
    }

    public TasksDto findByTitle(String title) {

        Optional<Task> currentTask = repository.findByTitle(title);
        return currentTask.map(converter::taskToTaskDto).orElse(null);

    }

    public List<TasksDto> getAllTasks() {
        return repository.findAll()
                .stream()
                .filter(task -> task.getRmv() != 1)
                .map(converter::taskToTaskDto)
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
