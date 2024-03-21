package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

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
        List<Task> history = manager.getHistory();

        for (Task task : history) {
            stringBuilder.append(task.getId()).append(",");
        }

        if (!history.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();

        if (value != null && !value.isEmpty()) {
            String[] ids = value.split(",");
            for (String id : ids) {
                try {
                    int taskId = Integer.parseInt(id.trim());
                    history.add(taskId);
                } catch (NumberFormatException e) {
                    System.err.println("ID: " + id + " не найден");
                }
            }
        }
        return history;
    }

    public void loadFromFile() throws IOException {
        boolean previousLineEmpty = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (previousLineEmpty) {
                        List<Integer> history = historyFromString(line);
                        fillHistory(history);
                        save();

                    } else {
                        String[] parts = line.split(",");
                        if (parts.length >= 5) {
                            String type = parts[1];
                            switch (type) {
                                case "TASK":
                                    Task task = Task.fromString(line);
                                    createTask(task);
                                    setIndex(task.getId());
                                    break;
                                case "EPIC":
                                    Epic epic = Epic.fromString(line);
                                    createEpic(epic);
                                    setIndex(epic.getId());
                                    break;
                                case "SUBTASK":
                                    SubTask subTask = SubTask.fromString(line);
                                    createSubTask(subTask);
                                    setIndex(subTask.getId());
                                    break;
                            }
                        }
                    }
                    previousLineEmpty = false;
                } else {
                    previousLineEmpty = true;
                }
            }
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("id,type,name,status,description,epic");
            writer.newLine();

            List<Task> tasks = super.getTasksList();
            for (Task task : tasks) {
                writer.write(task.toString() + ",");
                writer.newLine();
            }

            List<Epic> epics = super.getEpicsList();
            for (Epic epic : epics) {
                writer.write(epic.toString() + ",");
                writer.newLine();
            }

            List<SubTask> subTasks = super.getSubTasksList();
            for (SubTask subTask : subTasks) {
                writer.write(subTask.toString() + ",");
                writer.newLine();
            }

            writer.newLine();

            String historyString = historyToString(this.getHistoryManager());
            writer.write(historyString + ",");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillHistory(List<Integer> ids) {
        for (int id : ids) {
            boolean found = false;
            for (Task task : getTasksList()) {
                if (task.getId() == id) {
                    getHistoryManager().add(task);
                    found = true;
                    break;
                }
            }
            if (found) continue;

            for (Task task : getEpicsList()) {
                if (task.getId() == id) {
                    getHistoryManager().add(task);
                    found = true;
                    break;
                }
            }
            if (found) continue;

            for (Task task : getSubTasksList()) {
                if (task.getId() == id) {
                    getHistoryManager().add(task);
                    break;
                }
            }
        }

    }
}
