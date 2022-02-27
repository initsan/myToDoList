package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitleAndUserId(String title, Long userId);
    Optional<List<Task>> findTasksByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long id, Long userId);
    Optional<List<Task>> findTasksByIdAndUserId(Long id, Long userId);

}
