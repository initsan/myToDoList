package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.UserDto;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserMapper converter;
    private final UserRepository repository;

    @Autowired
    public UserService(UserMapper converter, UserRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }

    public UserDto createUser(UserDto userDto) {
        validateUser(userDto);
        if (userDto.getUsername() == null) {
            userDto.setUsername(userDto.getLogin());
        }
        userDto.setRmv(0);
        return converter.userToUserDto(repository.save(converter.userDtoToUser(userDto)));

    }

    private void validateUser(UserDto userDto) {
        if (isNull(userDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(userDto.getLogin())) {
            throw new ValidationException("Login is absence");
        }
    }

}
