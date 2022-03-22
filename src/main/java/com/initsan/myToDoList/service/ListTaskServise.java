package com.initsan.myToDoList.service;

import com.initsan.myToDoList.entity.TList;
import com.initsan.myToDoList.repository.TListRepository;
import com.initsan.myToDoList.repository.TasksRepository;
import com.initsan.myToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ListTaskServise {

    private final TListRepository tListRepository;
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListTaskServise(TListRepository tListRepository,
                           TasksRepository tasksRepository,
                           UserRepository userRepository) {
        this.tListRepository = tListRepository;
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    public boolean addTaskToList(Long listId, Long taskId, String userLogin) {
        TList result = null;
        var list = tListRepository.findById(listId);
        var task = tasksRepository.findById(taskId);
        Long userId = userRepository.getUserId(userLogin);
        if (list.isPresent() && task.isPresent()) {
            if (Objects.equals(task.get().getUserId(), userId) && Objects.equals(list.get().getUserId(), userId)) {
                var listResult = list.get();
                listResult.getTasks().add(task.get());
                result = tListRepository.save(listResult);
            }
        }
        return result != null;
    }

    public boolean removeTaskFromList(Long listId, Long taskId, String userLogin) {
        var list = tListRepository.findById(listId);
        var task = tasksRepository.findById(taskId);
        Long userId = userRepository.getUserId(userLogin);
        if (list.isPresent() && task.isPresent()) {
            if (Objects.equals(task.get().getUserId(), userId) && Objects.equals(list.get().getUserId(), userId)) {
                var listResult = list.get();
                listResult.getTasks().remove(task.get());
                tListRepository.save(listResult);
                return true;
            }
        }
        return false;
    }

    public Long addListToList(Long listSourceId, Long listResultId, String userLogin) {
        var listSource = tListRepository.findById(listSourceId);
        var listResult = tListRepository.findById(listResultId);
        Long userId = userRepository.getUserId(userLogin);

        if (listSource.isPresent() && listResult.isPresent()) {
            if (Objects.equals(listSource.get().getUserId(), userId) && Objects.equals(listResult.get().getUserId(), userId)) {
                var source = listSource.get().getTasks();
                var result = listResult.get();
                result.getTasks().addAll(source);
                tListRepository.save(result);
                return result.getId();
            }
        }
        return null;
    }
}
