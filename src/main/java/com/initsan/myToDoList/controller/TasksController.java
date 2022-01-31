package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dictionary.Status;
import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.service.TasksService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
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
@Log
public class TasksController {

    private final TasksService tasksService;

    @PostMapping("/add")
    public TasksDto addTask(@RequestBody TasksDto tasksDto) throws ValidationException {
        log.info(String.format("Add task: %s", tasksDto));
        return tasksService.addTask(tasksDto);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Integer id) {
        log.info(String.format("Remove task: %s", id));
        try {
            tasksService.removeTask(id);
            return ResponseEntity.ok().build();
        } catch (NullPointerException npe) {
            log.severe(String.format("Task %s not found", id));
            npe.printStackTrace();
            return ResponseEntity.notFound().build();

        }
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<TasksDto> changeStatus(@RequestParam Integer id, Status status) {
        log.info(String.format("Changing status task %s to %s", id, status));
        try {
            return ResponseEntity.ok(tasksService.changeStatus(id, status));
        } catch (NullPointerException npe) {
            log.severe(String.format("Task %s not found", id));
            npe.printStackTrace();
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<TasksDto> findByTitle(@RequestParam String title) {
        log.info(String.format("Searching task by title %s", title));
        var found = tasksService.findByTitle(title);
        if (!isNull(found)) {
            log.info(String.format("Founded %s", found));
            if (found.getRmv() == 1) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/allTasks")
    public List<TasksDto> getAllTasks() {
        log.info("Get task list");
        return tasksService.getAllTasks();
    }

}
