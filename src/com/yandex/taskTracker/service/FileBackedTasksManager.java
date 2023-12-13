package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.enums.Type;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    private Task fromString(String value) {
        String[] taskData = value.split(",");
        try {
            switch (Type.valueOf(taskData[1])) {
                case TASK:
                    Task task = new Task(taskData[2], taskData[4], Status.valueOf(taskData[3]));
                    task.setId(Integer.parseInt(taskData[0]));
                    return task;
                case EPIC:
                    Epic epic = new Epic(taskData[2], taskData[4]);
                    epic.setId(Integer.parseInt(taskData[0]));
                    epic.setStatus(Status.valueOf(taskData[3]));
                    return epic;
                case SUBTASK:
                    SubTask subTask = new SubTask(taskData[2], taskData[4], Status.valueOf(taskData[3])
                            , Integer.parseInt(taskData[5]));
                    subTask.setId(Integer.parseInt(taskData[0]));
                    subTask.setStatus(Status.valueOf(taskData[3]));
                    return subTask;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так!!!");
        }
        return null;
    }

    public static String historyToString(HistoryManager manager) {
        String[] arrayOfIds = new String[manager.getHistory().size()];
        for(int i = 0; i <= arrayOfIds.length; i++) {
            arrayOfIds[i] = String.valueOf(manager.getHistory().get(i).getId());
        }
        return String.join(",", arrayOfIds);
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

//            should be changed

            for (Task task: super.getHistory()) {
                fileWriter.write(task.getId() + ",");
            }
        } catch (IOException e) {
            System.out.println("Что-то пошло не так!!!");
        }
    }
}
