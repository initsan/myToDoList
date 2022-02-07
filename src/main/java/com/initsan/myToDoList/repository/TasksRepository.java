package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
}
