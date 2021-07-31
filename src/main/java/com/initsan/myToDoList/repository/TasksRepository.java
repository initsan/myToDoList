package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    Tasks findByTitle(String title);
}
