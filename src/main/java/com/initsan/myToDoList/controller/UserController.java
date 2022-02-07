package com.initsan.myToDoList.controller;

import com.initsan.myToDoList.dto.UserDto;
import com.initsan.myToDoList.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@ControllerAdvice
@Transactional(timeout = 120)
@Log
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info(String.format("Add task: %s %s", userDto.getId(), userDto.getLogin()));
        return userService.createUser(userDto);
    }

}
