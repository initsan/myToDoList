package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
