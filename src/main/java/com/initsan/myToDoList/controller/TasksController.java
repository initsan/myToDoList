package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.service.TasksService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Optional;

import static java.util.Objects.isNull;

@AllArgsConstructor
@RestController
@ControllerAdvice
@RequestMapping("/tasks")
@Transactional(timeout = 120)
@Log
public class TasksController {

    private final TasksService tasksService;

    @PostMapping("/add")
    public ResponseEntity<TasksDto> addTask(@RequestBody TasksDto tasksDto) throws ValidationException {
        var userLogin = getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add task: %s", tasksDto));
            return ResponseEntity.ok(tasksService.addTask(tasksDto, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Long id) {
        var userLogin = getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Remove task: %s", id));
            try {
                tasksService.removeTask(id, userLogin.get());
                return ResponseEntity.ok().build();
            } catch (NullPointerException npe) {
                log.severe(String.format("Task %s not found", id));
                npe.printStackTrace();
                return ResponseEntity.notFound().build();
            }
        } return ResponseEntity.notFound().build();
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<TasksDto> changeStatus(@RequestParam Long id, Status status) {
        var userLogin = getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Changing status task %s to %s", id, status));
            try {
                return ResponseEntity.ok(tasksService.changeStatus(id, status, userLogin.get()));
            } catch (NullPointerException npe) {
                log.severe(String.format("Task %s not found", id));
                npe.printStackTrace();
                return ResponseEntity.notFound().build();
            }
        } return ResponseEntity.notFound().build();
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<TasksDto> findByTitle(@RequestParam String title) {
        var userLogin = getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Searching task by title %s", title));
            var found = tasksService.findByTitle(title, userLogin.get());
            if (!isNull(found)) {
                log.info(String.format("Founded %s", found));
                if (found.getRmv() == 1) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(found);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/allTasks")
    public ResponseEntity<List<TasksDto>> getAllTasks() {
        var userLogin = getUserLogin();
        if (userLogin.isPresent()) {
            log.info("Get task list");
            var result = tasksService.getAllTasks(userLogin.get());
            if (result != null) {
                return ResponseEntity.ok(result);
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

    private Optional<String> getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> currentUserName = Optional.empty();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = Optional.ofNullable(authentication.getName());
        }
        return currentUserName;
    }

}
