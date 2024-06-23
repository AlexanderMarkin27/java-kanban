package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    private Task task1;
    private Task task2;
    private Task updatedTask;
    private Epic epic1;
    private Epic epic2;
    private SubTask subTask1;
    private SubTask subTask2;
    private SubTask subTask3;

    protected abstract T createTaskManager() throws IOException;

    @BeforeEach
    public void setup() throws IOException {
        taskManager = createTaskManager();
        task1 = new Task("Task 1", "task 1", Status.NEW, 45, LocalDateTime.now());
        task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS,
                45, LocalDateTime.now().plusMinutes(61));
        updatedTask = new Task("Updated task", "updated task", Status.NEW, 45, LocalDateTime.now());
        epic1 = new Epic("Epic 1", "Epic 1");
        epic2 = new Epic("Epic 2", "Epic 2");
        subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, 0, 15, LocalDateTime.now());
        subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, 0, 15, LocalDateTime.now().plusMinutes(20));
        subTask3 = new SubTask("SubTask 3", "SubTask 3", Status.NEW, 0, 15, LocalDateTime.now().plusMinutes(40));
    }

    @Test
    void getTasksList_returnList_emptyList() {
        assertEquals(taskManager.getTasksList().size(), 0);
    }

    @Test
    void getTaskList_returnList_twoTasks() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(taskManager.getTasksList().size(), 2);
    }

    @Test
    void deleteAllTasks_returnList_emptyList() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteAllTasks();
        assertEquals(taskManager.getTasksList().size(), 0);
    }

    @Test
    void getTaskByIndex_returnTask_indexOne() {
        taskManager.createTask(task2);
        assertEquals(taskManager.getTaskByIndex(1), task2);
    }

    @Test
    void createTask_addTaskToTasksList() {
        taskManager.createTask(task1);
        assertEquals(taskManager.getTasksList().get(0), task1);
    }

    @Test
    void updateTask_updateTaskInList() {
        taskManager.createTask(task1);
        task2.setId(task1.getId());
        taskManager.updateTask(task2);
        assertEquals(taskManager.getTasksList().get(0), task2);
    }

    @Test
    void deleteAllSubTasks_returnSubTaskList_empty() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        subTask2.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.deleteAllSubTasks();
        assertEquals(taskManager.getSubTasksList().size(), 0);
    }

    @Test
    void deleteAllSubTasks_deleteSubtasksInEpic() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        subTask2.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.deleteAllSubTasks();
        assertEquals(taskManager.getEpicByIndex(epicId).getSubTasks().size(), 0);
    }

    @Test
    void getSubTaskByIndex_returnSubTask_indexOne() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        assertEquals(taskManager.getSubTaskByIndex(subTask1.getId()), subTask1);
    }

    @Test
    void createdSubTask_addSubTaskInSubTasksList() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        assertEquals(taskManager.getSubTasksList().get(0), subTask1);
    }

    @Test
    void updateSubTask_updateSubTaskInList() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        subTask3.setId(subTask1.getId());
        subTask3.setEpicId(epicId);
        taskManager.updateSubTask(subTask3);
        assertEquals(taskManager.getSubTaskByIndex(subTask1.getId()), subTask3);

    }

    @Test
    void getEpicsList_returnList_empty() {
        assertEquals(taskManager.getEpicsList().size(), 0);
    }

    @Test
    void getEpicsList_returnList_twoEntries() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        assertEquals(taskManager.getEpicsList().size(), 2);
    }

    @Test
    void deleteAllEpics_returnEpicsList_empty() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.deleteAllEpics();
        assertEquals(taskManager.getEpicsList().size(), 0);
    }

    @Test
    void deleteAllEpics_deleteAllSubtasks() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        int epicId1 = epic1.getId();
        int epicId2 = epic2.getId();
        subTask1.setEpicId(epicId1);
        subTask2.setEpicId(epicId1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        subTask3.setEpicId(epicId2);
        taskManager.createSubTask(subTask3);
        taskManager.deleteAllEpics();
        assertEquals(taskManager.getSubTasksList().size(), 0);
    }

    @Test
    void getEpicsByIndex_returnEpic_indexOne() {
        taskManager.createEpic(epic1);
        assertEquals(taskManager.getEpicByIndex(epic1.getId()), epic1);
    }

    @Test
    void createdEpic_addEpicInEpicsList() {
        taskManager.createEpic(epic1);
        assertEquals(taskManager.getEpicsList().get(0), epic1);
    }

    @Test
    void updateEpic_updateEpicInList() {
        taskManager.createEpic(epic1);
        epic2.setId(epic1.getId());
        taskManager.updateEpic(epic2);
        assertEquals(taskManager.getEpicByIndex(epic1.getId()), epic2);
    }

    @Test
    void getSubTasksByEpic_returnAllSubtasksInEpic() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        int epicId1 = epic1.getId();
        subTask1.setEpicId(epicId1);
        subTask2.setEpicId(epicId1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertEquals(taskManager.getSubTasksByEpic(epicId1).size(), 2);
    }

    @Test
    void getHistory_returnHistoryList_empty() {
        assertEquals(taskManager.getHistory().size(), 0);

    }

    @Test
    void getHistory_returnHistoryList_twoEntries() {
        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.getTaskByIndex(task1.getId());
        taskManager.getEpicByIndex(epic1.getId());
        assertEquals(taskManager.getHistory().size(), 2);

    }

    @Test
    void getPrioritizedTasks_returnPrioritizedTasks_sortedByStartDate() {
        taskManager.createTask(task2);
        taskManager.createTask(task1);
        assertEquals(taskManager.getPrioritizedTasks().get(0), task1);
    }

    @Test
    void calculateEpicStatus_epicWithStatus_new() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        subTask2.setEpicId(epicId);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertEquals(epic1.getStatus(), Status.NEW);
    }

    @Test
    void calculateEpicStatus_epicWithStatus_done() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        subTask2.setEpicId(epicId);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertEquals(epic1.getStatus(), Status.DONE);
    }

    @Test
    void calculateEpicStatus_epicWithStatus_inProgress() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getId();
        subTask1.setEpicId(epicId);
        subTask2.setEpicId(epicId);
        subTask2.setStatus(Status.DONE);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);
    }

}
