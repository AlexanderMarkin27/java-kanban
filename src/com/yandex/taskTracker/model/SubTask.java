package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return  "SubTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", id=" + this.getId() +
                ", status=" + this.getStatus() +
                ", epic=" + this.epicId +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }
}
