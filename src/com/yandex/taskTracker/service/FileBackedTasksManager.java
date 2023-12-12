package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Integer createTask(Task task) {
        super.createTask(task);
        save();
        return task.getId();
    }

    @Override
    public Integer updateTask(Task task) {
        super.updateTask(task);
        save();
        return task.getId();
    }

    @Override
    public void deleteTaskByIndex(int id) {
        super.deleteTaskByIndex(id);
        save();
    }

    @Override
    public void deleteAllSubTasks() {
       super.deleteAllSubTasks();
       save();
    }

    @Override
    public Integer createSubTask(SubTask subTask) {
        super.createTask(subTask);
        save();
        return subTask.getId();
    }

    @Override
    public Integer updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
        return subTask.getId();
    }

    @Override
    public void deleteSubTaskByIndex(int id) {
        super.deleteSubTaskByIndex(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Integer createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public Integer updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public void deleteEpicByIndex(int id) {
       super.deleteEpicByIndex(id);
       save();

    }

    private void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.file, StandardCharsets.UTF_8))){
            String fileHeader = "id,type,name,status,description,epic";
            fileWriter.write(fileHeader + "\n");
            for (Task task: super.getTasksList()) {
                fileWriter.write(task.toString() + "\n");
            }
            for (SubTask subTask: super.getSubTasksList()) {
                fileWriter.write(subTask.toString() + "\n");
            }
            for (Epic epic: super.getEpicsList()) {
                fileWriter.write(epic.toString() + "\n");
            }
            fileWriter.write("\n");
            for (Task task: super.getHistory()) {
                fileWriter.write(task.getId() + ",");
            }
        } catch (IOException e) {
            System.out.println("Пиздец");
        }
    }
}
