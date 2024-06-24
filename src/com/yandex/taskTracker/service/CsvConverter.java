package com.yandex.taskTracker.service;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvConverter {

    public static String taskToString(Task task) {
        return task.getId() + ",TASK," + task.getName() + "," + task.getStatus() + "," + task.getDescription()
                + "," + (task.getDuration() == null ? "EMPTY" : task.getDuration().toMinutes()) + ","
                + (task.getStartTime() == null ? "EMPTY" : task.getStartTime());
    }

    public static String taskToString(Epic epic) {
        return epic.getId() + ",EPIC," + epic.getName() + "," + epic.getStatus() + "," + epic.getDescription()
                + "," + (epic.getDuration() == null ? "EMPTY" : epic.getDuration().toMinutes()) + ","
                + (epic.getStartTime() == null ? "EMPTY" : epic.getStartTime());
    }

    public static String taskToString(SubTask sub) {
        return sub.getId() + ",SUBTASK," + sub.getName() + "," + sub.getStatus() + "," + sub.getDescription()
                + "," + (sub.getDuration() == null ? "EMPTY" : sub.getDuration().toMinutes()) + ","
                + (sub.getStartTime() == null ? "EMPTY" : sub.getStartTime()) + "," + sub.getEpicId();
    }

    public static Task stringToTask(String[] strings) {
        int duration = Objects.equals(strings[5], "EMPTY") ? null : Integer.parseInt(strings[5]);
        LocalDateTime startTime = Objects.equals(strings[6], "EMPTY") ? null : LocalDateTime.parse(strings[6]);
        Task task = new Task(strings[2], strings[4], Status.valueOf(strings[3]),
                duration, startTime);
        task.setId(Integer.parseInt(strings[0]));
        return task;
    }

    public static Epic stringToEpic(String[] strings) {
        int duration = Objects.equals(strings[5], "EMPTY") ? null : Integer.parseInt(strings[5]);
        LocalDateTime startTime = Objects.equals(strings[6], "EMPTY") ? null : LocalDateTime.parse(strings[6]);
        Epic epic = new Epic(strings[2], strings[4]);
        epic.setId(Integer.parseInt(strings[0]));
        epic.setName(strings[2]);
        epic.setStatus(Status.valueOf(strings[3]));
        epic.setDuration(Duration.ofMinutes(duration));
        epic.setStartTime(startTime);
        return epic;
    }

    public static SubTask stringToSubTask(String[] strings) {
        int duration = Objects.equals(strings[5], "EMPTY") ? null : Integer.parseInt(strings[5]);
        LocalDateTime startTime = Objects.equals(strings[6], "EMPTY") ? null : LocalDateTime.parse(strings[6]);
        SubTask subTask = new SubTask(strings[2], strings[4], Status.valueOf(strings[3]), Integer.parseInt(strings[7]),
                duration, startTime);
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
