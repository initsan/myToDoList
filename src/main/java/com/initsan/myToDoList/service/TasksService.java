package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.entity.Task;
import com.initsan.myToDoList.exceptions.TaskNotFoundException;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.TasksRepository;
import com.initsan.myToDoList.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public TasksService(TasksRepository repository, TaskMapper converter, UserRepository userRepository) {
        this.repository = repository;
        this.converter = converter;
        this.userRepository = userRepository;
    }

    public TaskDto addTask(TaskDto taskDto, String userLogin) throws ValidationException {
        validateTaskDto(taskDto);
        taskDto.setStatus(Status.PROCESSING);
        taskDto.setCreateDate(LocalDateTime.now());
        taskDto.setUserId(userRepository.getUserId(userLogin));
        return converter.taskToTaskDto(repository.save(converter.taskDtoToTask(taskDto)));
    }

    public void removeTask(Long taskId, String userLogin) throws TaskNotFoundException{
        var currentTask = repository.findByIdAndUserId(taskId, userRepository.getUserId(userLogin));
        if (currentTask.isPresent()) {
            currentTask.get().setRmv(1);
            repository.save(currentTask.get());
        } else {
            throw new TaskNotFoundException(String.format("Task %s not found for user%s", taskId, userLogin));
        }
    }

    public TaskDto changeStatus(Long taskId, Status status, String userLogin) throws TaskNotFoundException{
        var currentTask = repository.findByIdAndUserId(taskId, userRepository.getUserId(userLogin));
        if (currentTask.isPresent()) {
            currentTask.get().setStatus(status);
            return converter.taskToTaskDto(repository.save(currentTask.get()));
        } else {
            throw new TaskNotFoundException(String.format("Task %s not found for user %s", taskId, userLogin));
        }
    }

    public TaskDto findByTitle(String title, String userLogin) {
        Optional<Task> currentTask = repository.findByTitleAndUserId(title, userRepository.getUserId(userLogin));
        return currentTask.map(converter::taskToTaskDto).orElse(null);

    }

    public List<TaskDto> getAllTasks(String userLogin) {
        List<Task> result = repository.findTasksByUserId(userRepository.getUserId(userLogin));
        if (result.isEmpty()) {
            return null;
        }
        return result
                .stream()
                .map(converter::taskToTaskDto)
                .collect(Collectors.toList());
    }

    private void validateTaskDto(TaskDto taskDto) throws ValidationException {
        if (isNull(taskDto)) {
            throw new ValidationException("Object task is null");
        }
        if (isNull(taskDto.getTitle())) {
            throw new ValidationException("Title is empty");
        }
    }

}
