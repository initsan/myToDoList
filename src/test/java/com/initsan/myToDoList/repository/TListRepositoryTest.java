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
class TListRepositoryTest {

    private final TListRepository tListRepository;

    @Autowired
    TListRepositoryTest(TListRepository tListRepository) {
        this.tListRepository = tListRepository;
    }

    @ParameterizedTest
    @MethodSource("getIdAndUserIdPositive")
    void findByIdAndUserIdPositiveTest(Long listId, Long userId) {
        var result = tListRepository.findByIdAndUserId(listId, userId);
        result.ifPresentOrElse(list -> Assertions.assertEquals(list.getRmv(), 0), () -> Assertions.fail("Result is empty"));
    }

    @ParameterizedTest
    @MethodSource("getIdAndUserIdNegative")
    void findByIdAndUserIdNegativeTest(Long listId, Long userId) {
        var result = tListRepository.findByIdAndUserId(listId, userId);
        Assertions.assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getListNameAndUserIdPositive")
    void findByNameAndUserIdPositiveTest(String listName, Long userId) {
        var result = tListRepository.findByNameAndUserId(listName, userId);
        result.ifPresentOrElse(list -> Assertions.assertEquals(list.getRmv(), 0), () -> Assertions.fail("Result is empty"));
    }

    @ParameterizedTest
    @MethodSource("getListNameAndUserIdNegative")
    void findByNameAndUserIdNegativeTest(String listName, Long userId) {
        var result = tListRepository.findByNameAndUserId(listName, userId);
        Assertions.assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {3})
    void findTListsByUserIdTest(Long userId) {
        var result = tListRepository.findTListsByUserId(userId);
        if (!result.isEmpty()) {
            result.forEach(list -> Assertions.assertEquals(list.getRmv(), 0));
        } else {
            Assertions.fail("Result is empty");
        }
    }

    public static Stream<Arguments> getIdAndUserIdPositive() {
        return Stream.of(
                Arguments.of(1L, 3L)
        );
    }

    public static Stream<Arguments> getIdAndUserIdNegative() {
        return Stream.of(
                Arguments.of(2L, 3L)
        );
    }

    public static Stream<Arguments> getListNameAndUserIdPositive() {
        return Stream.of(
                Arguments.of("list1", 3L)
        );
    }

    public static Stream<Arguments> getListNameAndUserIdNegative() {
        return Stream.of(
                Arguments.of("list2", 3L)
        );
    }

}