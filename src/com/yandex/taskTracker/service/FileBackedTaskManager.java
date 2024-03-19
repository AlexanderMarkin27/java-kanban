package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
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
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
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
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicByIndex(int id) {
        super.deleteEpicByIndex(id);
        save();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Task> tasks = manager.getHistory();
        for (Task task : tasks) {
            stringBuilder.append(task.toString()).append(",");
        }

        return stringBuilder.toString();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("taskManagerData.csv"))) {
            // Write header
            writer.write("id,type,name,status,description,epic");
            writer.newLine();

            // Write tasks
            List<Task> tasks = super.getTasksList();
            for (Task task : tasks) {
                writer.write(task.toString() + ",");
                writer.newLine();
            }

            // Write epics
            List<Epic> epics = super.getEpicsList();
            for (Epic epic : epics) {
                writer.write(epic.toString() + ",");
                writer.newLine();
            }

            // Write subtasks
            List<SubTask> subTasks = super.getSubTasksList();
            for (SubTask subTask : subTasks) {
                writer.write(subTask.toString() + ",");
                writer.newLine();
            }

            writer.write(FileBackedTaskManager.historyToString(historyManager));

            System.out.println("CSV data is saved in taskManagerData.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
