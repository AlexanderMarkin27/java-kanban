package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW, 0, LocalDateTime.now());
        this.subTasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return  "Epic{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", id=" + this.getId() +
                ", status=" + this.getStatus() +
                ", subTasks=" + this.subTasks +
                ", duration=" + this.getDuration().toMinutes() +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
