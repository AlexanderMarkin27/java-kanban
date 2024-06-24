package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void testTasksAreEqualIfIdsAreSame() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        Task task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS, 45, LocalDateTime.now().plusHours(1));
        task2.setId(1);

        assertEquals(task1, task2);
    }

    @Test
    public void testTasksAreNotEqualIfIdsAreDifferent() {
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task1.setId(1);

        Task task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS, 45, LocalDateTime.now().plusHours(1));
        task2.setId(2);

        assertNotEquals(task1, task2);
    }

}