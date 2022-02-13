package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByLogin(String login);
}
