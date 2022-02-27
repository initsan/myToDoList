package com.initsan.myToDoList.repository;

import com.initsan.myToDoList.entity.TList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TListRepository extends JpaRepository<TList, Long> {
    Optional<TList> findByIdAndUserId(Long id, Long userId);
    Optional<TList> findByNameAndUserId(String listName, Long userId);
    Optional<List<TList>> findTListsByUserId(Long userId);
}
