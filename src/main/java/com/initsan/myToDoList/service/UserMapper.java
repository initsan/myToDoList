package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.UserDto;
import com.initsan.myToDoList.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

}
