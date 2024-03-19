package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Integer id;
    private Status status;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
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

    public int getId() {
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

//    @Override
//    public String toString() {
//        return "Task{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", id=" + id +
//                ", status=" + status +
//                '}';
//    }

    @Override
    public String toString() {
        return id + ",TASK," + name + "," + status + "," + description;
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid format for Task string");
        }
        Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]));
        task.setId(Integer.parseInt(parts[0]));
        return task;
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
