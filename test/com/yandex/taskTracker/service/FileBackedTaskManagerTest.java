package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    File file = new File("testFile.csv");
    @Override
    protected FileBackedTaskManager createTaskManager() throws IOException {
        return FileBackedTaskManager.loadFromFile( File.createTempFile("taskManagerData", ".csv"),
                 Managers.getDefaultHistory());
    }


    @Test
    void saveInFile_taskSaved_taskLoadedFromFile() throws IOException {
        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(file, Managers.getDefaultHistory());
        Task task1 = new Task("Task 1", "task 1", Status.NEW, 45, LocalDateTime.now());
        manager.createTask(task1);

        FileBackedTaskManager manager1 = FileBackedTaskManager.loadFromFile(file, Managers.getDefaultHistory());
        assertEquals(manager1.getTaskByIndex(task1.getId()), task1);
    }


}