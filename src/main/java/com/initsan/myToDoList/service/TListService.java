package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TListDto;
import com.initsan.myToDoList.dto.TaskDto;
import com.initsan.myToDoList.entity.TList;
import com.initsan.myToDoList.exceptions.ListNotFoundException;
import com.initsan.myToDoList.exceptions.ValidationException;
import com.initsan.myToDoList.repository.TListRepository;
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
    private final TaskMapper taskMapper;

    @Autowired
    public TListService(TListRepository repository, TListMapper mapper, UserRepository userRepository, TaskMapper taskMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    public TListDto addTList(TListDto tListDto, String userLogin) throws ValidationException{
        validateTListDto(tListDto);
        tListDto.setUserId(userRepository.getUserId(userLogin));
        return mapper.tListToTListDto(repository.save(mapper.tListDtoToTList(tListDto)));
    }

    public void removeTaskList(Long tListId, String userLogin) throws ListNotFoundException{
        var currentTaskList = repository.findByIdAndUserId(tListId, userRepository.getUserId(userLogin));
        if (currentTaskList.isPresent()) {
            repository.delete(currentTaskList.get());
        } else {
            throw new ListNotFoundException(String.format("List %s not found for user%s", tListId, userLogin));
        }
    }

    public TListDto findList(String tListName, String userLogin) {
        Optional<TList> currentTList = repository.findByNameAndUserId(tListName, userRepository.getUserId(userLogin));
        return currentTList.map(mapper::tListToTListDto).orElse(null);
    }

    public List<TListDto> getAllTLists(String userLogin) {
        List<TList> result = repository.findTListsByUserId(userRepository.getUserId(userLogin));
        if (result.isEmpty()) {
            return null;
        }
        return result
                .stream()
                .map(mapper::tListToTListDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getListTask(Long listId, String userLogin) {
        Long userId = userRepository.getUserId(userLogin);
        List<TaskDto> listTaskResult = new ArrayList<>();
        var resultList = repository.findByIdAndUserId(listId, userId);
        resultList.ifPresent(tList -> tList.getTasks().forEach(task -> {
            listTaskResult.add(taskMapper.taskToTaskDto(task));
        }));
        return listTaskResult;
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
