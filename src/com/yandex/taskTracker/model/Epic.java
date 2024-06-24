package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Epic extends Task{

    private LocalDateTime epicEndTime;
    private ArrayList<Integer> subTasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW, 0, LocalDateTime.now());
        this.subTasks = new ArrayList<>();
        this.epicEndTime = null;
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
                ", endTime=" + this.epicEndTime +
                '}';
    }

    public void setEpicEndTime(LocalDateTime endTime) {
        this.epicEndTime = endTime;
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
