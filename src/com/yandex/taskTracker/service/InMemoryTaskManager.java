package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int index = 0;
    private Map<Integer, Task> tasksList;
    private Map<Integer, Epic> epicsList;
    private Map<Integer, SubTask> subTasksList;

    private HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        tasksList = new HashMap<>();
        epicsList = new HashMap<>();
        subTasksList = new HashMap<>();
    }

    /* Функции для задач */
    @Override
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasksList.values());
    }

    @Override
    public void deleteAllTasks() {
        for (int taskId: tasksList.keySet()) {
            historyManager.remove(taskId);
        }
        tasksList.clear();
    }

    @Override
    public Task getTaskByIndex(int id) {
        historyManager.add(tasksList.get(id));
        return tasksList.get(id);
    }

    @Override
    public Integer createTask(Task task) {
        task.setId(getIndex());
        tasksList.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public Integer updateTask(Task task) {
        if (tasksList.containsKey(task.getId())) {
            tasksList.put(task.getId(), task);
            return task.getId();
        }
        return null;
    }

    @Override
    public void deleteTaskByIndex(int id) {
        historyManager.remove(id);
        tasksList.remove(id);
    }

    /* Функции для подзадач */

    @Override
    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasksList.values());
    }

    /**
     * Метод удаляет все подзадачи из эпиков, меняет статус эпика на 'NEW'
     * Затем очищает лист подзадач
     * @return Пустой лист подзадач
     */
    @Override
    public void deleteAllSubTasks() {
        for (Epic epic: epicsList.values()) {
            epic.getSubTasks().clear();
            epic.setStatus(Status.NEW);
        }
        for (int subTasksId: subTasksList.keySet()) {
            historyManager.remove(subTasksId);
        }
        subTasksList.clear();
    }

    @Override
    public SubTask getSubTaskByIndex(int id) {
        historyManager.add(subTasksList.get(id));
        return subTasksList.get(id);
    }

    @Override
    public Integer createSubTask(SubTask subTask) {
        try {
            subTask.setId(getIndex());
            subTasksList.put(subTask.getId(), subTask);
            Epic epic = epicsList.get(subTask.getEpicId());
            epic.getSubTasks().add(subTask.getId());
            setEpicStatus(epic);
            return subTask.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
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
    @Override
    public void deleteSubTaskByIndex(int id) {
        if (subTasksList.get(id) != null) {
            int epicId = subTasksList.get(id).getEpicId();
            epicsList.get(epicId).getSubTasks().remove(Integer.valueOf(id));
            setEpicStatus(epicsList.get(epicId));
            historyManager.remove(id);
            subTasksList.remove(id);
        }
    }

    /* Функции для эпиков */

    @Override
    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epicsList.values());
    }

    /**
     * Метод лист подзадач, т.к. подзадача выполняется в рамках эпика,
     * После очищает лист эпиков
     * @return Пустой лист эпиков
     */
    @Override
    public void deleteAllEpics() {
        for (int subTasksId: subTasksList.keySet()) {
            historyManager.remove(subTasksId);
        }
        subTasksList.clear();
        for (int epicId: epicsList.keySet()) {
            historyManager.remove(epicId);
        }
        epicsList.clear();
    }

    @Override
    public Epic getEpicByIndex(int id) {
        historyManager.add(epicsList.get(id));
        return epicsList.get(id);
    }

    @Override
    public Integer createEpic(Epic epic) {
        epic.setId(getIndex());
        epicsList.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public Integer updateEpic(Epic epic) {
        try {
            epicsList.get(epic.getId()).setName(epic.getName());
            epicsList.get(epic.getId()).setDescription(epic.getDescription());
            return epic.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void deleteEpicByIndex(int id) {
        try {
            for (Integer subTaskId: epicsList.get(id).getSubTasks()) {
                historyManager.remove(subTaskId);
                subTasksList.remove(subTaskId);
            }
            historyManager.remove(id);
            epicsList.remove(id);
        } catch (Exception ex) {
            System.out.println("Эпика с этим ID нет в системе");
        }

    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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