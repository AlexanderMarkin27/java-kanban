package com.yandex.taskTracker.service;

import com.yandex.taskTracker.exception.ManagerSaveException;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import java.io.*;
import java.util.*;

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

    private void loadFromFile() {
        boolean previousLineEmpty = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
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
                                    tasksList.put(task.getId(), task);
                                    break;
                                case "EPIC":
                                    Epic epic = CsvConverter.stringToEpic(parts);
                                    epicsList.put(epic.getId(), epic);
                                    break;
                                case "SUBTASK":
                                    SubTask subTask = CsvConverter.stringToSubTask(parts);
                                    subTasksList.put(subTask.getId(), subTask);
                                    break;
                            }
                        }
                    }
                    previousLineEmpty = false;
                } else {
                    previousLineEmpty = true;
                }
            }

            ArrayList<Task> listOfAllObjectsInFile = getListOfAllObjectsInFile(getEpicsList(), getTasksList(), getSubTasksList());

            index = getMaxId(listOfAllObjectsInFile);

            addSubTasksToEpics(getSubTasksList());

            fillHistory(history, listOfAllObjectsInFile);
        } catch (IOException e){
            throw new ManagerSaveException("Ошибка чтения из файла");
        }
    }

    private void save(){
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
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    private void fillHistory(List<Integer> ids, ArrayList<Task> tasks) {
       tasks.stream().filter(task -> ids.contains(task.getId())).forEach(task -> historyManager.add(task));
    }

    private Integer getMaxId(ArrayList<Task> tasks) {
        try {
            return tasks.stream().max(Comparator.comparing(Task::getId)).get().getId();
        } catch (NoSuchElementException e) {
            return 0;
        }

    }

    private ArrayList<Task> getListOfAllObjectsInFile(ArrayList<Epic> epicArrayList, ArrayList<Task> taskArrayList, ArrayList<SubTask> subTaskArrayList) {
        ArrayList<Task> tasks = new ArrayList<>(taskArrayList);
        tasks.addAll(epicArrayList);
        tasks.addAll(subTaskArrayList);
        return tasks;
    }

    private void addSubTasksToEpics(ArrayList<SubTask> subTaskArrayList) {
        subTaskArrayList.forEach(subTask -> {
            Epic epic = epicsList.get(subTask.getEpicId());
            epic.getSubTasks().add(subTask.getId());
        });
    }

}
