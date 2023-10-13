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
        try {
            return tasksList.get(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Integer createTask(Task task) {
        task.setId(index);
        tasksList.put(task.getId(), task);
        index++;
        return task.getId();
    }

    public Integer updateTask(int id, Task task) {
        if (tasksList.containsKey(id)) {
            task.setId(id);
            tasksList.put(task.getId(), task);
            return id;
        }
        return null;
    }

    public HashMap<Integer, Task> deleteTaskByIndex(int id) {
        tasksList.remove(id);
        return tasksList;
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
            setEpicStatus(epic);
        }
        subTasksList.clear();
    }

    public SubTask getSubTaskByIndex(int id) {
        try {
            return subTasksList.get(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Integer createSubTask(SubTask subTask) {
        try {
            subTask.setId(index);
            subTasksList.put(subTask.getId(), subTask);
            Epic epic = epicsList.get(subTask.getEpicId());
            epic.getSubTasks().add(subTask.getId());
            updateEpic(epic.getId(), epic);
            index++;
            return subTask.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    public Object updateSubTask(int id, SubTask subTask) {
        if (subTasksList.containsKey(id)) {
            subTask.setId(id);
            subTasksList.put(subTask.getId(), subTask);
            for (Epic epic: epicsList.values()) {
                if (epic.getSubTasks().contains(id)) {
                    setEpicStatus(epic);
                }
            }
            return id;
        }
        return null;
    }

    /**
     * Метод удаляет подзадачу из эпиков
     * Затем удаляет саму подзадачу
     * @return лист подзадач
     */
    public HashMap<Integer, SubTask> deleteSubTaskByIndex(int id) {
        for (Epic epic: epicsList.values()) {
            epic.getSubTasks().remove(Integer.valueOf(id));
            setEpicStatus(epic);
        }
        subTasksList.remove(id);
        return subTasksList;
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
        try {
            return epicsList.get(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Epic createEpic(Epic epic) {
        epic.setId(index);
        setEpicStatus(epic);
        epicsList.put(epic.getId(), epic);
        index++;
        return epicsList.get(epic.getId());
    }

    public Integer updateEpic(int id, Epic epic) {
        if (epicsList.containsKey(id)) {
            epic.setId(id);
            setEpicStatus(epic);
            epicsList.put(epic.getId(), epic);
            return id;
        }
        return null;
    }

    public HashMap<Integer, Epic> deleteEpicByIndex(int id) {
        try {
            for (Integer subTaskId: epicsList.get(id).getSubTasks()) {
                subTasksList.remove(subTaskId);
            }
            epicsList.remove(id);
            return epicsList;
        } catch (Exception ex) {
            return null;
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
        if (epicHasNoSubTasks(epic) || checkSubtasksStatus(epic.getSubTasks(), Status.NEW)) {
            epic.setStatus(Status.NEW);
        } else if (checkSubtasksStatus(epic.getSubTasks(), Status.DONE)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private boolean epicHasNoSubTasks(Epic epic) {
        return epic.getSubTasks().isEmpty();
    }

    private boolean checkSubtasksStatus(ArrayList<Integer> list, Status status) {
        for (int subTaskId: list) {
            if (subTasksList.get(subTaskId).getStatus() != status) {
                return false;
            }
        }
        return true;
    }
}