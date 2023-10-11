import util.Status;

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
    public HashMap<Integer, Task> getTasksList() {
        return tasksList;
    }

    public HashMap<Integer, Task> deleteAllTasks() {
        tasksList.clear();
        return tasksList;
    }

    public Task getTaskByIndex(int id) {
        try {
            return tasksList.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer createTask(Task task) {
        task.setId(index);
        tasksList.put(task.getId(), task);
        index++;
        return task.getId();
    }

    public Object updateTask(int id, Task task) {
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

    public HashMap<Integer, SubTask> getSubTasksList() {
        return subTasksList;
    }

    /**
     * Метод удаляет все подзадачи из эпиков
     * Затем очищает лист подзадач
     * @return Пустой лист подзадач
     */
    public HashMap<Integer, SubTask> deleteAllSubTasks() {
        for (Integer subTaskId: subTasksList.keySet()) {
            for (Epic epic: epicsList.values()) {
                epic.getSubTasks().remove(subTaskId);
                updateEpic(epic.getId(), epic);
            }
        }
        subTasksList.clear();
        return subTasksList;
    }

    public SubTask getSubTaskByIndex(int id) {
        try {
            return subTasksList.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer createSubTask(SubTask subTask) {
        subTask.setId(index);
        tasksList.put(subTask.getId(), subTask);
        index++;
        return subTask.getId();
    }

    public Object updateSubTask(int id, SubTask subTask) {
        if (subTasksList.containsKey(id)) {
            subTask.setId(id);
            subTasksList.put(subTask.getId(), subTask);
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
            updateEpic(epic.getId(), epic);
        }
        subTasksList.remove(id);
        return subTasksList;
    }

    /* Функции для эпиков */

    public HashMap<Integer, Epic> getEpicsList() {
        return epicsList;
    }

    /**
     * Метод лист подзадач, т.к. подзадача выполняется в рамках эпика,
     * После очищает лист эпиков
     * @return Пустой лист эпиков
     */
    public HashMap<Integer, Epic> deleteAllEpics() {
        subTasksList.clear();
        epicsList.clear();
        return epicsList;
    }

    public Epic getEpicByIndex(int id) {
        try {
            return epicsList.get(id);
        } catch (Exception e) {
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

    public Epic updateEpic(int id, Epic epic) {
        epic.setId(id);
        setEpicStatus(epic);
        epicsList.put(epic.getId(), epic);
        return epicsList.get(id);
    }

    public HashMap<Integer, Epic> deleteEpicByIndex(int id) {
        for (Integer subTaskId: epicsList.get(id).getSubTasks()) {
            subTasksList.remove(subTaskId);
        }
        epicsList.remove(id);
        return epicsList;
    }

    public ArrayList<SubTask> getSubTasksByEpic(Epic epic) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>(epic.getSubTasks().size());
        for (int subTaskId: epic.getSubTasks()) {
            epicSubTasks.add(subTasksList.get(subTaskId));
        }
        return epicSubTasks;
    }



    private void setEpicStatus(Epic epic) {
        boolean subTasksListIsEmpty = !epicHasSubTasks(epic);
        if (subTasksListIsEmpty || checkSubtasksStatus(epic.getSubTasks(), Status.NEW)) {
            epic.setStatus(Status.NEW);
        } else if (checkSubtasksStatus(epic.getSubTasks(), Status.DONE)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private boolean epicHasSubTasks(Epic epic) {
        return !epic.getSubTasks().isEmpty();
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
