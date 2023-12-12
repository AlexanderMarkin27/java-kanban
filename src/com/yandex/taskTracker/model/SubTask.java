package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.enums.Type;

public class SubTask extends Task {
    private final int epicId;
    private final Type type;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
        this.type = Type.SUBTASK;
    }

    @Override
    public String toString() {
        return this.getId() + "," + type + "," + this.getName() + "," + this.getStatus()
                + ","  + this.getDescription() + "," + this.getEpicId();
    }

    public int getEpicId() {
        return epicId;
    }
}
