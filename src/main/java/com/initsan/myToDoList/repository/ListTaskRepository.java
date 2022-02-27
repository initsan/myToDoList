package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.ListTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListTaskRepository extends JpaRepository<ListTask, Long> {
    Optional<ListTask> findByListIdAndTaskId (Long listId, Long taskId);
    Optional<List<ListTask>> findListTasksByListId (Long listId);

}
