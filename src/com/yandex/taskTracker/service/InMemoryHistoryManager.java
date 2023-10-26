package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> tasksHistory = new ArrayList<>();
    @Override
    public void add(Task task) {
        if (task != null) {
            if (tasksHistory.size() < 10) {
                tasksHistory.add(task);
            } else {
                tasksHistory.remove(0);
                tasksHistory.add(task);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        for (Task task: tasksHistory) {
            if (task == null) {
                tasksHistory.remove(null);
            }
        }
        return tasksHistory;
    }
}
