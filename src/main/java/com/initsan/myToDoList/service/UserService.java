package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.UserDto;
import com.initsan.myToDoList.entity.User;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper converter;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper converter, UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.converter = converter;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        validateUser(userDto);
        if (repository.findByLogin(userDto.getLogin()) != null) {
            throw new ValidationException("User exists");
        }
        if (userDto.getUsername() == null) {
            userDto.setUsername(userDto.getLogin());
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
