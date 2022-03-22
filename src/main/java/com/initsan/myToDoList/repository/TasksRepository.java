package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.TList;
import com.initsan.myToDoList.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitleAndUserId(String title, Long userId);
    List<Task> findTasksByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long id, Long userId);

}
