package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.service.TasksService;
import com.initsan.myToDoList.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@AllArgsConstructor
@RestController
@ControllerAdvice
@RequestMapping("/tasks")
@Transactional(timeout = 120)
@Log
public class TasksController {

    private final TasksService tasksService;
    private final UserService userService;

    @PostMapping("/addTask")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) throws ValidationException {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add task: %s", taskDto));
            return ResponseEntity.ok(tasksService.addTask(taskDto, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/removeTask/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Long id) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Remove task: %s", id));
            tasksService.removeTask(id, userLogin.get());
            return ResponseEntity.ok().build();
        } return ResponseEntity.notFound().build();
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<TaskDto> changeStatus(@RequestParam Long id, Status status) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Changing status task %s to %s", id, status));
            return ResponseEntity.ok(tasksService.changeStatus(id, status, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

    @GetMapping("/findTaskByTitle")
    public ResponseEntity<TaskDto> findByTitle(@RequestParam String title) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Searching task by title %s", title));
            var found = tasksService.findByTitle(title, userLogin.get());
            if (!isNull(found)) {
                log.info(String.format("Founded %s", found));
                return ResponseEntity.ok(found);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/allTasks")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info("Get task list");
            var result = tasksService.getAllTasks(userLogin.get());
            if (result != null) {
                return ResponseEntity.ok(result);
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

}
