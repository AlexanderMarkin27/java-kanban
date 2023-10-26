package com.yandex.taskTracker.utils;

import com.yandex.taskTracker.service.HistoryManager;
import com.yandex.taskTracker.service.InMemoryHistoryManager;
import com.yandex.taskTracker.service.InMemoryTaskManager;
import com.yandex.taskTracker.service.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}