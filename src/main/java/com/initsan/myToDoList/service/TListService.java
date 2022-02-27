package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TListDto;
import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.entity.ListTask;
import com.initsan.myToDoList.entity.TList;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.ListTaskRepository;
import com.initsan.myToDoList.repository.TListRepository;
import com.initsan.myToDoList.repository.TasksRepository;
import com.initsan.myToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class TListService {

    private final TListRepository repository;
    private final TListMapper mapper;
    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final ListTaskRepository listTaskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TListService(TListRepository repository, TListMapper mapper, UserRepository userRepository, TasksRepository tasksRepository, ListTaskRepository listTaskRepository, TaskMapper taskMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.listTaskRepository = listTaskRepository;
        this.taskMapper = taskMapper;
    }


    public TListDto addTList(TListDto tListDto, String userLogin) {
        validateTListDto(tListDto);
        tListDto.setUserId(userRepository.getUserId(userLogin));
        tListDto.setRmv(0);
        return mapper.tListToTListDto(repository.save(mapper.tListDtoToTList(tListDto)));
    }

    public void removeTaskList(Long tListId, String userLogin) {
        var currentTaskList = repository.findByIdAndUserId(tListId, userRepository.getUserId(userLogin));
        if (currentTaskList.isPresent()) {
            currentTaskList.get().setRmv(1);
            repository.save(currentTaskList.get());
        } else {
            throw new NullPointerException(String.format("List %s not found for user%s", tListId, userLogin));
        }
    }

    public TListDto findList(String tListName, String userLogin) {
        Optional<TList> currentTList = repository.findByNameAndUserId(tListName, userRepository.getUserId(userLogin));
        return currentTList.map(mapper::tListToTListDto).orElse(null);
    }

    public List<TListDto> getAllTLists(String userLogin) {
        Optional<List<TList>> result = repository.findTListsByUserId(userRepository.getUserId(userLogin));
        if (result.isEmpty()) {
            return null;
        }
        return result.get()
                .stream()
                .filter(task -> task.getRmv() != 1)
                .map(mapper::tListToTListDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getListTask(Long listId, String userLogin) {
        ArrayList<TaskDto> lListTaskResult = new ArrayList<>();
        Optional<List<ListTask>> lt = listTaskRepository.findListTasksByListId(listId);
        lt.ifPresent(listTasks -> listTasks.forEach(t -> {
            var task = tasksRepository.findByIdAndUserId(t.getTaskId(), userRepository.getUserId(userLogin));
            task.ifPresent(value -> lListTaskResult.add(taskMapper.taskToTaskDto(value)));
        }));
        return lListTaskResult;
    }

    private void validateTListDto(TListDto tListDto) throws ValidationException {
        if (isNull(tListDto)) {
            throw new ValidationException("Object task is null");
        }
        if (isNull(tListDto.getName())) {
            throw new ValidationException("Title is empty");
        }
    }
}
