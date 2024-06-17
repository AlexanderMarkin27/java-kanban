package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.utils.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Override
    @Test
    void getTaskListShouldReturnEmptyList() {
        super.getTaskListShouldReturnEmptyList();
    }

    @Override
    @Test
    void getTaskListShouldReturnListWithTwoEntries() {
        super.getTaskListShouldReturnListWithTwoEntries();
    }

    @Override
    @Test
    void deleteAllTasksShouldReturnEmptyTaskList() {
        super.deleteAllTasksShouldReturnEmptyTaskList();
    }

    //    @Test
//    public void testEpicStatusAllSubtaskStatusNew() {
//        Epic epic = new Epic("Epic 1", "Epic 1");
//        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, 1, 15, LocalDateTime.now());
//        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, 1, 15, LocalDateTime.now().plusMinutes(20));
//        SubTask subTask3 = new SubTask("SubTask 3", "SubTask 3", Status.NEW, 1, 15, LocalDateTime.now().plusMinutes(40));
//
//        taskManager.createEpic(epic);
//        taskManager.createSubTask(subTask1);
//        taskManager.createSubTask(subTask2);
//        taskManager.createSubTask(subTask3);
//
//        assertEquals(taskManager.getEpicByIndex(1).getStatus(), Status.NEW);
//    }
//
//    @Test
//    public void testEpicStatusAllSubtaskStatusDone() {
//        Epic epic = new Epic("Epic 1", "Epic 1");
//        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.DONE, 1, 15, LocalDateTime.now());
//        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.DONE, 1, 15, LocalDateTime.now().plusMinutes(20));
//        SubTask subTask3 = new SubTask("SubTask 3", "SubTask 3", Status.DONE, 1, 15, LocalDateTime.now().plusMinutes(40));
//
//        taskManager.createEpic(epic);
//        taskManager.createSubTask(subTask1);
//        taskManager.createSubTask(subTask2);
//        taskManager.createSubTask(subTask3);
//
//        assertEquals(taskManager.getEpicByIndex(1).getStatus(), Status.DONE);
//    }
//
//    @Test
//    public void testEpicStatusSubtaskStatusNewAndDone() {
//        Epic epic = new Epic("Epic 1", "Epic 1");
//        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, 1, 15, LocalDateTime.now());
//        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.DONE, 1, 15, LocalDateTime.now().plusMinutes(20));
//
//        taskManager.createEpic(epic);
//        taskManager.createSubTask(subTask1);
//        taskManager.createSubTask(subTask2);
//
//        assertEquals(taskManager.getEpicByIndex(1).getStatus(), Status.NEW);
//    }
//
//    @Test
//    public void testEpicStatusSubtaskStatusInProgress() {
//        Epic epic = new Epic("Epic 1", "Epic 1");
//        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.IN_PROGRESS, 1, 15, LocalDateTime.now());
//        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.IN_PROGRESS, 1, 15, LocalDateTime.now().plusMinutes(20));
//
//        taskManager.createEpic(epic);
//        taskManager.createSubTask(subTask1);
//        taskManager.createSubTask(subTask2);
//
//        assertEquals(taskManager.getEpicByIndex(1).getStatus(), Status.IN_PROGRESS);
//    }

}