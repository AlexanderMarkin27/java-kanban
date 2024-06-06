package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;
    private HistoryManager historyManager;

    private FileBackedTaskManager(File file, HistoryManager historyManager) {
        this.file = file;
        this.historyManager = historyManager;
    }

    public static FileBackedTaskManager getInstance(File file, HistoryManager historyManager) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file, historyManager);
        manager.loadFromFile();

        return manager;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Integer createTask(Task task) {
        int id = super.createTask(task);
        save();
        return id;
    }

    @Override
    public Integer updateTask(Task task) {
        int id = super.updateTask(task);
        save();
        return id;
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
        int id = super.createSubTask(subTask);
        save();
        return id;
    }

    @Override
    public Integer updateSubTask(SubTask subTask) {
        int id = super.updateSubTask(subTask);
        save();
        return id;
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
        int id = super.createEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer updateEpic(Epic epic) {
        int id = super.updateEpic(epic);
        save();
        return id;
    }

    @Override
    public void deleteEpicByIndex(int id) {
        super.deleteEpicByIndex(id);
        save();
    }

    public void loadFromFile() throws IOException {
        boolean previousLineEmpty = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<Task> taskArrayList = getTasksList();
            ArrayList<Epic> epicArrayList = getEpicsList();
            ArrayList<SubTask> subTaskArrayList = getSubTasksList();
            List<Integer> history = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (previousLineEmpty) {
                        history.addAll(CsvConverter.historyFromString(line));
                    } else {
                        String[] parts = line.split(",");
                        if (parts.length >= 5) {
                            String type = parts[1];
                            switch (type) {
                                case "TASK":
                                    Task task = CsvConverter.stringToTask(parts);
                                    taskArrayList.add(task);
                                    break;
                                case "EPIC":
                                    Epic epic = CsvConverter.stringToEpic(parts);
                                    epicArrayList.add(epic);
                                    break;
                                case "SUBTASK":
                                    SubTask subTask = CsvConverter.stringToSubTask(parts);
                                    subTaskArrayList.add(subTask);
                                    break;
                            }
                        }
                    }
                    previousLineEmpty = false;
                } else {
                    previousLineEmpty = true;
                }
            }
            epicArrayList.forEach(this::createEpic);
            taskArrayList.forEach(this::createTask);
            subTaskArrayList.forEach(this::createSubTask);
            fillHistory(history, epicArrayList, taskArrayList, subTaskArrayList);
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("id,type,name,status,description,epic");
            writer.newLine();

            List<Task> tasks = super.getTasksList();
            for (Task task : tasks) {
                writer.write(CsvConverter.taskToString(task) + ",");
                writer.newLine();
            }

            List<Epic> epics = super.getEpicsList();
            for (Epic epic : epics) {
                writer.write(CsvConverter.taskToString(epic) + ",");
                writer.newLine();
            }

            List<SubTask> subTasks = super.getSubTasksList();
            for (SubTask subTask : subTasks) {
                writer.write(CsvConverter.taskToString(subTask) + ",");
                writer.newLine();
            }

            writer.newLine();

            String historyString = CsvConverter.historyToString(this.getHistory());
            writer.write(historyString + ",");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillHistory(List<Integer> ids, ArrayList<Epic> epicArrayList, ArrayList<Task> taskArrayList, ArrayList<SubTask> subTaskArrayList) {
       ArrayList<Task> tasks = new ArrayList<>(taskArrayList);
       tasks.addAll(epicArrayList);
       tasks.addAll(subTaskArrayList);

       tasks.stream().filter(task -> ids.contains(task.getId())).forEach(task -> historyManager.add(task));
    }

}
