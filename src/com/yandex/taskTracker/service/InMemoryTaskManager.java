package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    protected int index = 0;
    protected final Map<Integer, Task> tasksList;
    protected final Map<Integer, Epic> epicsList;
    protected final Map<Integer, SubTask> subTasksList;

    private final Set<Task> prioritizedTasks;

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
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
        for (int taskId : tasksList.keySet()) {
            historyManager.remove(taskId);
        }
        getTasksList().forEach(prioritizedTasks::remove);
        tasksList.clear();
    }

    @Override
    public Task getTaskByIndex(int id) {
        Task task = tasksList.get(id);
        if (task != null) {
            historyManager.add(tasksList.get(id));
        }
        return task;
    }

    @Override
    public Integer createTask(Task task) {
        if (tasksDoNotOverlap(task)) {
            task.setId(getIndex());
            tasksList.put(task.getId(), task);
            addToPrioritizedTaskList(task);
            return task.getId();
        }
        else {
            return null;
        }

    }

    @Override
    public Integer updateTask(Task task) {
        if (tasksList.containsKey(task.getId()) && tasksDoNotOverlap(task)) {
            tasksDoNotOverlap(task);
            tasksList.put(task.getId(), task);
            addToPrioritizedTaskList(task);
            return task.getId();
        }
        return null;
    }

    @Override
    public void deleteTaskByIndex(int id) {
        Task task = tasksList.get(id);
        prioritizedTasks.remove(task);
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
     *
     */
    @Override
    public void deleteAllSubTasks() {
        for (Epic epic : epicsList.values()) {
            epic.getSubTasks().clear();
            epic.setStatus(Status.NEW);
        }
        for (int subTasksId : subTasksList.keySet()) {
            historyManager.remove(subTasksId);
        }
        getSubTasksList().forEach(prioritizedTasks::remove);
        subTasksList.clear();
    }

    @Override
    public SubTask getSubTaskByIndex(int id) {
        SubTask subTask = subTasksList.get(id);
        if (subTask != null) {
            historyManager.add(subTasksList.get(id));
        }
        return subTask;
    }

    @Override
    public Integer createSubTask(SubTask subTask) {
        try {
            if (tasksDoNotOverlap(subTask)) {
                subTask.setId(getIndex());
                subTasksList.put(subTask.getId(), subTask);
                Epic epic = epicsList.get(subTask.getEpicId());
                epic.getSubTasks().add(subTask.getId());
                addToPrioritizedTaskList(subTask);
                updateEpicData(epic);
                return subTask.getId();
            }

        } catch (Exception ex) {
            System.out.println("Error create Subtask");
        }
        return null;
    }

    @Override
    public Integer updateSubTask(SubTask subTask) {
        try {
            if (tasksDoNotOverlap(subTask)) {
                subTasksList.put(subTask.getId(), subTask);
                Epic epic = epicsList.get(subTask.getEpicId());
                updateEpicData(epic);
                addToPrioritizedTaskList(subTask);
                return subTask.getId();
            }

        } catch (Exception ex) {
            System.out.println("Error update subtask");
        }
        return null;
    }

    /**
     * Метод удаляет подзадачу из эпиков
     * Затем удаляет саму подзадачу
     *
     */
    @Override
    public void deleteSubTaskByIndex(int id) {
        SubTask subTask = subTasksList.get(id);
        if (subTask != null) {
            int epicId = subTask.getEpicId();
            epicsList.get(epicId).getSubTasks().remove(Integer.valueOf(id));
            updateEpicData(epicsList.get(epicId));
            historyManager.remove(id);
            prioritizedTasks.remove(subTask);
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
     *
     */
    @Override
    public void deleteAllEpics() {
        for (int subTasksId : subTasksList.keySet()) {
            historyManager.remove(subTasksId);
        }
        for (SubTask subTask: subTasksList.values()) {
            prioritizedTasks.remove(subTask);
        }
        subTasksList.clear();
        for (int epicId : epicsList.keySet()) {
            historyManager.remove(epicId);
        }
        epicsList.clear();
    }

    @Override
    public Epic getEpicByIndex(int id) {
        Epic epic = epicsList.get(id);
        if (epic != null) {
            historyManager.add(epicsList.get(id));
        }
        return epic;
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
            System.out.println("Error update epic");
        }
        return null;
    }

    @Override
    public void deleteEpicByIndex(int id) {
        try {
            epicsList.get(id).getSubTasks().forEach(subTaskId -> {
                historyManager.remove(subTaskId);
                SubTask subTask = subTasksList.get(subTaskId);
                prioritizedTasks.remove(subTask);
                subTasksList.remove(subTaskId);
            });
            historyManager.remove(id);
            epicsList.remove(id);
        } catch (Exception ex) {
            System.out.println("Эпика с этим ID нет в системе");
        }

    }

    @Override
    public List<SubTask> getSubTasksByEpic(int epicId) {
        try {
            return epicsList.get(epicId).getSubTasks().stream()
                    .map(subTasksList::get)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private int getIndex() {
        index++;
        return index;
    }

    private void addToPrioritizedTaskList(Task task) {
        if (task.getStartTime() != null && tasksDoNotOverlap(task)) {
            prioritizedTasks.add(task);
        }
    }

    private boolean tasksDoNotOverlap(Task newTask) {
        return prioritizedTasks.stream().noneMatch(taskOverlap(newTask));
    }

    private Predicate<Task> taskOverlap(Task other) {
        return task -> (!(task.getEndTime().isBefore(other.getStartTime())
                || task.getStartTime().isAfter(other.getEndTime()))) && !Objects.equals(task.getId(), other.getId());
    }

    private void updateEpicData(Epic epic) {
        setEpicStatus(epic);
        setEpicDuration(epic);
        setEpicStartTime(epic);
        calculateEpicEndTime(epic);
    }

    private void setEpicStatus(Epic epic) {
        int statusNewCounter = 0;
        int statusDoneCounter = 0;

        for (int subTaskId : epic.getSubTasks()) {
            switch (subTasksList.get(subTaskId).getStatus()) {
                case NEW -> statusNewCounter++;
                case DONE -> statusDoneCounter++;
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

    private void calculateEpicEndTime(Epic epic) {
        Optional<LocalDateTime> latestEndTime = epic.getSubTasks().stream()
                .map(subTaskId -> subTasksList.get(subTaskId).getEndTime())
                .max(LocalDateTime::compareTo);

        latestEndTime.ifPresent(epic::setEpicEndTime);
    }

    private void setEpicDuration(Epic epic) {
        Duration sum = epic.getSubTasks().stream()
                .map(subTaskId -> subTasksList.get(subTaskId).getDuration())
                .reduce(Duration.ZERO, Duration::plus);
        epic.setDuration(sum);
    }

    private void setEpicStartTime(Epic epic) {
        Optional<LocalDateTime> earliestStartTime = epic.getSubTasks().stream()
                .map(subTaskId -> subTasksList.get(subTaskId).getStartTime())
                .min(LocalDateTime::compareTo);

        earliestStartTime.ifPresent(epic::setStartTime);
    }
}