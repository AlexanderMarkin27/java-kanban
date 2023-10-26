package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();
}