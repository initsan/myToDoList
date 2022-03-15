package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dto.TListDto;
import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.exceptions.ListNotFoundException;
import com.initsan.myToDoList.service.ListTaskServise;
import com.initsan.myToDoList.service.TListService;
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
@RequestMapping("/lists")
@Transactional(timeout = 120)
@Log
public class TListController {

    private final TListService tListService;
    private final UserService userService;
    private final ListTaskServise listTaskServise;

    @PostMapping("/addList")
    public ResponseEntity<TListDto> addTList(@RequestBody TListDto tListDto) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add list: %s", tListDto));
            return ResponseEntity.ok(tListService.addTList(tListDto, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/removeTaskList/{id}")
    public ResponseEntity<Void> removeTList(@PathVariable Long id) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Remove list: %s", id));
            tListService.removeTaskList(id, userLogin.get());
            return ResponseEntity.ok().build();
        } return ResponseEntity.notFound().build();
    }

    @GetMapping("/findList")
    public ResponseEntity<TListDto> findByTitle(@RequestParam String listName) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Searching list by listName %s", listName));
            var found = tListService.findList(listName, userLogin.get());
            if (!isNull(found)) {
                log.info(String.format("Founded %s", found));
                return ResponseEntity.ok(found);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/allLists")
    public ResponseEntity<List<TListDto>> getAllTLists() {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info("Get task list");
            var result = tListService.getAllTLists(userLogin.get());
            if (result != null) {
                return ResponseEntity.ok(result);
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("addTaskToList")
    public ResponseEntity<Void> addTaskToList(@RequestBody Long listId, Long taskId) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add list %s to task %s", listId, taskId));
            if (listTaskServise.addTaskToList(listId, taskId, userLogin.get())) {
                return ResponseEntity.ok().build();
            }
        } return ResponseEntity.notFound().build();
    }

    @PostMapping("removeTaskFromList")
    public ResponseEntity<Void> removeTaskFromList(@RequestBody Long listId, Long taskId) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add list %s to task %s", listId, taskId));
            if (listTaskServise.removeTaskFromList(listId, taskId, userLogin.get())) {
                return ResponseEntity.ok().build();
            }
        } return ResponseEntity.notFound().build();
    }

    @PostMapping("addListToList")
    public ResponseEntity<List<TaskDto>> addListToList(@RequestBody Long listSourceId, Long listResultId) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Add list %s to list %s", listSourceId, listResultId));
            var resListId = listTaskServise.addListToList(listSourceId, listResultId, userLogin.get());
            return ResponseEntity.ok(tListService.getListTask(resListId, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

    @GetMapping("findTasksList")
    public ResponseEntity<List<TaskDto>> findTasksList(@RequestBody Long listId) {
        var userLogin = userService.getUserLogin();
        if (userLogin.isPresent()) {
            log.info(String.format("Find list %s", listId));
            return ResponseEntity.ok(tListService.getListTask(listId, userLogin.get()));
        } return ResponseEntity.notFound().build();
    }

}
