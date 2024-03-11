package com.yandex.taskTracker.model;

import org.junit.jupiter.api.Test;
import com.yandex.taskTracker.enums.Status;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTasksAreEquals() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);

        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
        task2.setId(1);

        assertTrue(task1.equals(task2));
    }

    @Test
    void testSubTasksAreEquals() {
        SubTask subTask1 = new SubTask("SubTask 1", "SubDescription 1", Status.NEW, 1);
        subTask1.setId(1);

        SubTask subTask2 = new SubTask("SubTask 2", "SubDescription 2", Status.IN_PROGRESS, 1);
        subTask2.setId(1);

        // Check if the two subtasks are equal
        assertTrue(subTask1.equals(subTask2));
    }

}