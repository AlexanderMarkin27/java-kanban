package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TasksManager {
    private int index = 0;
    private HashMap<Integer, Task> tasksList;
    private HashMap<Integer, Epic> epicsList;
    private HashMap<Integer, SubTask> subTasksList;


    public TasksManager() {
        tasksList = new HashMap<>();
        epicsList = new HashMap<>();
        subTasksList = new HashMap<>();
    }

    /* Функции для задач */
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasksList.values());
    }

    public void deleteAllTasks() {
        tasksList.clear();
    }

    public Task getTaskByIndex(int id) {
        return tasksList.get(id);
    }

    public Integer createTask(Task task) {
        int id = getIndex();
        task.setId(id);
        tasksList.put(task.getId(), task);
        return task.getId();
    }

    public Integer updateTask(Task task) {
        if (tasksList.containsKey(task.getId())) {
            tasksList.put(task.getId(), task);
            return task.getId();
        }
        return null;
    }

    public void deleteTaskByIndex(int id) {
        tasksList.remove(id);
    }

    /* Функции для подзадач */

    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasksList.values());
    }

    /**
     * Метод удаляет все подзадачи из эпиков, меняет статус эпика на 'NEW'
     * Затем очищает лист подзадач
     * @return Пустой лист подзадач
     */
    public void deleteAllSubTasks() {
        for (Epic epic: epicsList.values()) {
            epic.getSubTasks().clear();
            epic.setStatus(Status.NEW);
        }
        subTasksList.clear();
    }

    public SubTask getSubTaskByIndex(int id) {
        return subTasksList.get(id);
    }

    public Integer createSubTask(SubTask subTask) {
        try {
            int id = getIndex();
            subTask.setId(id);
            subTasksList.put(subTask.getId(), subTask);
            Epic epic = epicsList.get(subTask.getEpicId());
            epic.getSubTasks().add(subTask.getId());
            return subTask.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    public Integer updateSubTask(SubTask subTask) {
        try {
            subTasksList.put(subTask.getId(), subTask);
            Epic epic = epicsList.get(subTask.getEpicId());
            setEpicStatus(epic);
            return subTask.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Метод удаляет подзадачу из эпиков
     * Затем удаляет саму подзадачу
     * @return лист подзадач
     */
    public void deleteSubTaskByIndex(int id) {
        if (subTasksList.get(id) != null) {
            int epicId = subTasksList.get(id).getEpicId();
            epicsList.get(epicId).getSubTasks().remove(id);
            setEpicStatus(epicsList.get(epicId));
            subTasksList.remove(id);
        }
    }

    /* Функции для эпиков */

    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epicsList.values());
    }

    /**
     * Метод лист подзадач, т.к. подзадача выполняется в рамках эпика,
     * После очищает лист эпиков
     * @return Пустой лист эпиков
     */
    public void deleteAllEpics() {
        subTasksList.clear();
        epicsList.clear();
    }

    public Epic getEpicByIndex(int id) {
        return epicsList.get(id);
    }

    public Integer createEpic(Epic epic) {
        int id = getIndex();
        epic.setId(id);
        epicsList.put(epic.getId(), epic);
        return epic.getId();
    }

    public Integer updateEpic(Epic epic) {
        try {
            epicsList.get(epic.getId()).setName(epic.getName());
            epicsList.get(epic.getId()).setDescription(epic.getDescription());
            return epic.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    public void deleteEpicByIndex(int id) {
        try {
            for (Integer subTaskId: epicsList.get(id).getSubTasks()) {
                subTasksList.remove(subTaskId);
            }
            epicsList.remove(id);
        } catch (Exception ex) {
            System.out.println("Эпика с этим ID нет в системе");
        }

    }

    public ArrayList<SubTask> getSubTasksByEpic(int epicId) {
        try {
            ArrayList<SubTask> epicSubTasks = new ArrayList<>(epicsList.get(epicId).getSubTasks().size());
            for (int subTaskId: epicsList.get(epicId).getSubTasks()) {
                epicSubTasks.add(subTasksList.get(subTaskId));
            }
            return epicSubTasks;
        } catch (Exception ex) {
            return null;
        }

    }

    private void setEpicStatus(Epic epic) {
        int statusNewCounter = 0;
        int statusDoneCounter = 0;

        for (int subTaskId: epic.getSubTasks()) {
            switch (subTasksList.get(subTaskId).getStatus()) {
                case NEW:
                    statusNewCounter++;
                    break;
                case DONE:
                    statusDoneCounter++;
                    break;
            }
        }

        if (epic.getSubTasks().isEmpty() || statusNewCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (statusDoneCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private int getIndex() {
        index++;
        return index;
    }
}