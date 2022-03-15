package com.initsan.myToDoList.service;

import com.initsan.myToDoList.entity.ListTask;
import com.initsan.myToDoList.repository.ListTaskRepository;
import com.initsan.myToDoList.repository.TListRepository;
import com.initsan.myToDoList.repository.TasksRepository;
import com.initsan.myToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ListTaskServise {

    private final ListTaskRepository listTaskRepository;
    private final TListRepository tListRepository;
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListTaskServise(ListTaskRepository listTaskRepository,
                           TListRepository tListRepository,
                           TasksRepository tasksRepository,
                           UserRepository userRepository,
                           TListService tListService) {
        this.listTaskRepository = listTaskRepository;
        this.tListRepository = tListRepository;
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    public boolean addTaskToList(Long listId, Long taskId, String userLogin) {
        ListTask result = null;
        var list = tListRepository.findById(listId);
        var task = tasksRepository.findById(taskId);
        Long userId = userRepository.getUserId(userLogin);
        if (list.isPresent() && task.isPresent()) {
            if (Objects.equals(task.get().getUserId(), userId) && Objects.equals(list.get().getUserId(), userId)) {
                result = listTaskRepository.save(new ListTask(listId, taskId));
            }
        }
        return result != null;
    }

    public boolean removeTaskFromList(Long listId, Long taskId, String userLogin) {
        var list = tListRepository.findById(listId);
        var task = tasksRepository.findById(taskId);
        var listTask = listTaskRepository.findByListIdAndTaskId(listId, taskId);
        Long userId = userRepository.getUserId(userLogin);
        if (list.isPresent() && task.isPresent() && listTask.isPresent()) {
            if (Objects.equals(task.get().getUserId(), userId) && Objects.equals(list.get().getUserId(), userId)) {
                listTaskRepository.delete(listTask.get());
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
                var resList = listTaskRepository.findListTasksByListId(listSource.get().getId());
                if (!resList.isEmpty()) {
                    // TO DO сделать метод в репозитории, чтобы сразу за один запрос ставил нужным таскам новый лист
                    resList.forEach(listTask -> {
                        listTask.setListId(listResult.get().getId());
                        listTaskRepository.save(listTask);
                    });
                    return listResultId;
                }
            }
        }
        return null;
    }
}
