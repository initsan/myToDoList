package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dto.TasksDto;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.service.TasksService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@AllArgsConstructor
@RestController
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
        tasksService.removeTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByTitle")
    public TasksDto findByTitle(@RequestParam String title) {
        log.info(String.format("Searching task by title %s", title));
        var found = tasksService.findByTitle(title);
        if (!isNull(found)) {
            log.info(String.format("Founded %s", found));
            return found;
        }
        return null;
    }

    @GetMapping("/allTasks")
    public List<TasksDto> getAllTasks() {
        log.info("Get task list");
        return tasksService.getAllTasks();
    }

}
