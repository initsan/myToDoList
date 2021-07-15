package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
}
