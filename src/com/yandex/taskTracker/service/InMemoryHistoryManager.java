package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Task;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int TASK_HISTORY_LIST_SIZE = 10;
    private LinkedList<Task> tasksHistory = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (tasksHistory.size() >= TASK_HISTORY_LIST_SIZE) {
                tasksHistory.removeFirst();
            }
            tasksHistory.addLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(tasksHistory);
    }
}
