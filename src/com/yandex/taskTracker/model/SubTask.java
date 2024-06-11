package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status status, int epicId, int durationInMinutes, LocalDateTime startTime) {
        super(name, description, status, durationInMinutes, startTime);
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
                ", duration=" + this.getDuration().toMinutes() +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }
}
