package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.entity.Task;
import com.initsan.myToDoList.entity.UserData;
import com.initsan.myToDoList.exceptions.UserNotFoundException;
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

    public TasksDto addTask(TasksDto tasksDto, String userLogin) throws ValidationException {
        validateTaskDto(tasksDto);
        tasksDto.setStatus(Status.PROCESSING);
        tasksDto.setCreateDate(LocalDateTime.now());
        tasksDto.setUserId(getUserId(userLogin));
        tasksDto.setRmv(0);
        return converter.taskToTaskDto(repository.save(converter.taskDtoToTask(tasksDto)));
    }

    public void removeTask(Long taskId, String userLogin) {
        var currentTask = repository.findByIdAndUserId(taskId, getUserId(userLogin));
        if (currentTask.isPresent()) {
            currentTask.get().setRmv(1);
            repository.save(currentTask.get());
        } else {
            throw new NullPointerException(String.format("Task %s not found for user%s", taskId, userLogin));
        }
    }

    public TasksDto changeStatus(Long taskId, Status status, String userLogin) {
        var currentTask = repository.findByIdAndUserId(taskId, getUserId(userLogin));
        if (currentTask.isPresent()) {
            currentTask.get().setStatus(status);
            return converter.taskToTaskDto(repository.save(currentTask.get()));
        } else {
            throw new NullPointerException(String.format("Task %s not found for user %s", taskId, userLogin));
        }
    }

    public TasksDto findByTitle(String title, String userLogin) {
        Optional<Task> currentTask = repository.findByTitleAndUserId(title, getUserId(userLogin));
        return currentTask.map(converter::taskToTaskDto).orElse(null);

    }

    public List<TasksDto> getAllTasks(String userLogin) {
        Optional<List<Task>> result = repository.findTasksByUserId(getUserId(userLogin));
        if (result.isEmpty()) {
            return null;
        }
        return result.get()
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

    private Long getUserId(String userLogin) {
        Optional<UserData> userData = userRepository.findByLogin(userLogin);
        if (userData.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userData.get().getId();
    }

}
