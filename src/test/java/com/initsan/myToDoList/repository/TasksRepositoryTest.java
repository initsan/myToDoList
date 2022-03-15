package com.initsan.myToDoList.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Stream;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TasksRepositoryTest {

    private final TasksRepository tasksRepository;

    @Autowired
    TasksRepositoryTest(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @ParameterizedTest
    @MethodSource("getTitleAndUserIdPositive")
    void findByTitleAndUserIdPositiveTest(String title, Long userId) {
        var result = tasksRepository.findByTitleAndUserId(title, userId);
        result.ifPresentOrElse(task -> Assertions.assertEquals(task.getRmv(), 0), () -> Assertions.fail("Result is empty"));
    }

    @ParameterizedTest
    @MethodSource("getTitleAndUserIdNegative")
    void findByTitleAndUserIdNegativeTest(String title, Long userId) {
        var result = tasksRepository.findByTitleAndUserId(title, userId);
        Assertions.assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3})
    void findTasksByUserId(Long userId) {
        var result = tasksRepository.findTasksByUserId(userId);
        if (!result.isEmpty()) {
            result.forEach(task -> Assertions.assertEquals(task.getRmv(), 0));
        } else {
            Assertions.fail("Result is empty");
        }
    }

    @ParameterizedTest
    @MethodSource("getTaskIdAndUserIdPositive")
    void findByIdAndUserIdPositiveTest(Long taskId, Long userId) {
        var result = tasksRepository.findByIdAndUserId(taskId, userId);
        result.ifPresentOrElse(task -> Assertions.assertEquals(task.getRmv(), 0), () -> Assertions.fail("Result is empty"));
    }

    @ParameterizedTest
    @MethodSource("getTaskIdAndUserIdNegative")
    void findByIdAndUserIdNegativeTest(Long taskId, Long userId) {
        var result = tasksRepository.findByIdAndUserId(taskId, userId);
        Assertions.assertTrue(result.isEmpty());
    }

    public static Stream<Arguments> getTitleAndUserIdPositive() {
        return Stream.of(
                Arguments.of("Test Title", 1L)
        );
    }

    public static Stream<Arguments> getTitleAndUserIdNegative() {
        return Stream.of(
                Arguments.of("Test Title 25", 3L)
        );
    }

    public static Stream<Arguments> getTaskIdAndUserIdPositive() {
        return Stream.of(
                Arguments.of(1L, 1L)
        );
    }

    public static Stream<Arguments> getTaskIdAndUserIdNegative() {
        return Stream.of(
                Arguments.of(25L, 3L)
        );
    }

}