package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvConverter {

    public static String taskToString(Task task) {
        return task.getId() + ",TASK," + task.getName() + "," + task.getStatus() + "," + task.getDescription()
                + "," + task.getDuration().toMinutes() + "," + task.getStartTime();
    }

    public static String taskToString(Epic epic) {
        return epic.getId() + ",EPIC," + epic.getName() + "," + epic.getStatus() + "," + epic.getDescription()
                + "," + epic.getDuration().toMinutes() + "," + epic.getStartTime();
    }

    public static String taskToString(SubTask sub) {
        return sub.getId() + ",SUBTASK," + sub.getName() + "," + sub.getStatus() + "," + sub.getDescription()
                + "," + sub.getDuration().toMinutes() + "," + sub.getStartTime() + "," + sub.getEpicId();
    }

    public static Task stringToTask(String[] strings) {
        Task task = new Task(strings[2], strings[4], Status.valueOf(strings[3]),
                Integer.parseInt(strings[5]), LocalDateTime.parse(strings[6]));
        task.setId(Integer.parseInt(strings[0]));
        return task;
    }

    public static Epic stringToEpic(String[] strings) {
        Epic epic = new Epic(strings[2], strings[4]);
        epic.setId(Integer.parseInt(strings[0]));
        epic.setName(strings[2]);
        epic.setStatus(Status.valueOf(strings[3]));
        epic.setDuration(Duration.ofMinutes(Integer.parseInt(strings[5])));
        epic.setStartTime(LocalDateTime.parse(strings[6]));
        return epic;
    }

    public static SubTask stringToSubTask(String[] strings) {
        SubTask subTask = new SubTask(strings[2], strings[4], Status.valueOf(strings[3]), Integer.parseInt(strings[7]),
                Integer.parseInt(strings[5]), LocalDateTime.parse(strings[6]));
        subTask.setId(Integer.parseInt(strings[0]));
        return subTask;
    }

    public static String historyToString(List<Task> history) {
        StringBuilder stringBuilder = new StringBuilder();

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
}
