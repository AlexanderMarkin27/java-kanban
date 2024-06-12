package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected int index = 0;
    protected final Map<Integer, Task> tasksList;
    protected final Map<Integer, Epic> epicsList;
    protected final Map<Integer, SubTask> subTasksList;

    private final Set<Task> prioritizedTasks;

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private final Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getStartTime().compareTo(t2.getStartTime());
        }
    };

    public InMemoryTaskManager() {
        prioritizedTasks = new TreeSet<Task>(comparator);
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
        if (!taskHasOverlap(task)) {
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
        if (tasksList.containsKey(task.getId()) && !taskHasOverlap(task)) {
            tasksList.put(task.getId(), task);
            addToPrioritizedTaskList(task);
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
     *
     * @return Пустой лист подзадач
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
            if (!taskHasOverlap(subTask)) {
                subTask.setId(getIndex());
                subTasksList.put(subTask.getId(), subTask);
                Epic epic = epicsList.get(subTask.getEpicId());
                epic.getSubTasks().add(subTask.getId());
                addToPrioritizedTaskList(subTask);
                updateEpicData(epic);
                return subTask.getId();
            }

        } catch (Exception ex) {
            System.out.println("Oooops...");
        }
        return null;
    }

    @Override
    public Integer updateSubTask(SubTask subTask) {
        try {
            if (!taskHasOverlap(subTask)) {
                subTasksList.put(subTask.getId(), subTask);
                Epic epic = epicsList.get(subTask.getEpicId());
                updateEpicData(epic);
                addToPrioritizedTaskList(subTask);
                return subTask.getId();
            }

        } catch (Exception ex) {
            System.out.println("Oooops...");
        }
        return null;
    }

    /**
     * Метод удаляет подзадачу из эпиков
     * Затем удаляет саму подзадачу
     *
     * @return лист подзадач
     */
    @Override
    public void deleteSubTaskByIndex(int id) {
        if (subTasksList.get(id) != null) {
            int epicId = subTasksList.get(id).getEpicId();
            epicsList.get(epicId).getSubTasks().remove(Integer.valueOf(id));
            updateEpicData(epicsList.get(epicId));
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
     *
     * @return Пустой лист эпиков
     */
    @Override
    public void deleteAllEpics() {
        for (int subTasksId : subTasksList.keySet()) {
            historyManager.remove(subTasksId);
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
            System.out.println("Oooops...");
        }
        return null;
    }

    @Override
    public void deleteEpicByIndex(int id) {
        try {
            for (Integer subTaskId : epicsList.get(id).getSubTasks()) {
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
            for (int subTaskId : epicsList.get(epicId).getSubTasks()) {
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

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private int getIndex() {
        index++;
        return index;
    }

    private void addToPrioritizedTaskList(Task task) {
        if (task.getStartTime() != null && !taskHasOverlap(task)) {
            prioritizedTasks.add(task);
        }
    }

    private boolean taskHasOverlap(Task newTask) {
        boolean hasOverlap = false;
        for (Task task: prioritizedTasks) {
            if (!compareTasksCompletionTime(task, newTask)) {
                hasOverlap = true;
                break;
            }
        }
        return hasOverlap;
    }

    private boolean compareTasksCompletionTime(Task t1, Task t2) {
        return t1.getEndTime().isBefore(t2.getStartTime()) && t2.getEndTime().isBefore(t1.getStartTime());
    }

    private void updateEpicData(Epic epic) {
        setEpicStatus(epic);
        setEpicDuration(epic);
        setEpicStartTime(epic);
    }

    private void setEpicStatus(Epic epic) {
        int statusNewCounter = 0;
        int statusDoneCounter = 0;

        for (int subTaskId : epic.getSubTasks()) {
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