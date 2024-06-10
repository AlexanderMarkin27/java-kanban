package com.yandex.taskTracker.utils;

import com.yandex.taskTracker.service.*;

import java.io.File;
import java.io.IOException;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackedTaskManager(File file, HistoryManager historyManager) throws IOException {
        return FileBackedTaskManager.getInstance(file, historyManager);
    }
}