package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.UserData;
import com.initsan.myToDoList.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByLogin(String login);

    default Long getUserId(String userLogin) {
        Optional<UserData> userData = findByLogin(userLogin);
        if (userData.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userData.get().getId();
    }
}
