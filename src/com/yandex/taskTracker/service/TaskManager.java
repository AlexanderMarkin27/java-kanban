package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getTasksList();

    void deleteAllTasks();

    Task getTaskByIndex(int id);

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTaskByIndex(int id);

    ArrayList<SubTask> getSubTasksList();

    void deleteAllSubTasks();

    SubTask getSubTaskByIndex(int id);

    void createSubTask(SubTask subTask);

    void updateSubTask(SubTask subTask);

    void deleteSubTaskByIndex(int id);

    ArrayList<Epic> getEpicsList();

    void deleteAllEpics();

    Epic getEpicByIndex(int id);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpicByIndex(int id);

    ArrayList<SubTask> getSubTasksByEpic(int epicId);

    List<Task> getHistory();
}
