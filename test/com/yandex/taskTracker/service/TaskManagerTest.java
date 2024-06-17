package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    private T taskManager;
    private Task task1;
    private Task task2;

    protected abstract T createTaskManager();

    @BeforeEach
    public void setup() {
        taskManager = createTaskManager();
        task1 = new Task("Task 1", "task 1", Status.NEW, 60, LocalDateTime.now());
        task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS,
                45, LocalDateTime.now().plusMinutes(61));
    }

    @Test
    void getTaskListShouldReturnEmptyList() {
        assertEquals(taskManager.getTasksList().size(), 0);
    }

    @Test
    void getTaskListShouldReturnListWithTwoEntries() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(taskManager.getTasksList().size(), 2);
    }

    @Test
    void deleteAllTasksShouldReturnEmptyTaskList() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteAllTasks();
        assertEquals(taskManager.getTasksList().size(), 0);
    }

    @Test
    void taskByIndexShouldReturnTaskForIndex() {

    }

    @Test
    void createdTaskShouldBeInTasksList() {

    }

    @Test
    void updateTaskShouldUpdateTaskInList() {

    }

    @Test
    void getSubTaskShouldReturnListOfSubTasks() {

    }

    @Test
    void deleteAllSubTasksShouldDeleteAllSubTasks() {

    }

    @Test
    void subTaskByIndexShouldReturnSubTaskForIndex() {

    }

    @Test
    void createdSubTaskShouldBeInSubTasksList() {

    }

    @Test
    void updateSubTaskShouldUpdateSubTaskInList() {

    }

    @Test
    void getEpicsShouldReturnListOfEpics() {

    }

    @Test
    void deleteAllEpicsShouldDeleteAllEpics() {

    }

    @Test
    void epicsByIndexShouldReturnEpicForIndex() {

    }

    @Test
    void createdEpicShouldBeInEpicsList() {

    }

    @Test
    void updateEpicShouldUpdateEpicInList() {

    }

    @Test
    void subTaskByEpicShouldReturnAllSubtasksInEpic() {

    }

    @Test
    void getHistoryShouldReturnHistoryList() {

    }

    @Test
    void getPrioritizedTasksShouldReturnTasksListSortedByStartDate() {

    }

    @Test
    void calculateEpicStatus() {

    }

}
