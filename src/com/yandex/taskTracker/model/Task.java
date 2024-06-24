package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Integer id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String description, Status status, Integer durationInMin, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(durationInMin);
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        try {
            return startTime.plusMinutes(duration.toMinutes());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", duration=" + duration.toMinutes() +
                ", startTime=" + startTime +
                ", endTime=" + getEndTime() +
                '}';
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.id);
    }
}
