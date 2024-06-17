package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {

    HistoryManager manager;

    @BeforeEach
    public void setup() {
        manager = Managers.getDefaultHistory();
    }

    @Test
    void historyListShouldBeEmpty() {
        assertEquals(manager.getHistory().size(), 0);
    }

    @Test
    void notPossibleToAddTaskWithSameIdInHistory() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        manager.add(task1);
        manager.add(task1);
        assertEquals(manager.getHistory().size(), 1);
    }

    @Test
    void removeFirstTaskFromHistoryList() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        Task task2 = new Task("Task 2", "task 2", Status.NEW, 60, LocalDateTime.now());
        task2.setId(2);

        Task task3 = new Task("Task 3", "task 3", Status.NEW, 60, LocalDateTime.now());
        task3.setId(3);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(1);
        assertEquals(manager.getHistory().get(0), task2);
    }

    @Test
    void removeTaskInTheMiddleOfHistoryList() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        Task task2 = new Task("Task 2", "task 2", Status.NEW, 60, LocalDateTime.now());
        task2.setId(2);

        Task task3 = new Task("Task 3", "task 3", Status.NEW, 60, LocalDateTime.now());
        task3.setId(3);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(2);
        assertEquals(manager.getHistory().get(1), task3);
    }

    @Test
    void removeLastTaskFromHistoryList() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        Task task2 = new Task("Task 2", "task 2", Status.NEW, 60, LocalDateTime.now());
        task2.setId(2);

        Task task3 = new Task("Task 3", "task 3", Status.NEW, 60, LocalDateTime.now());
        task3.setId(3);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(3);
        assertEquals(manager.getHistory().get(1), task2);
    }
}
