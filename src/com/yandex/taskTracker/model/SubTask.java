package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

//    @Override
//    public String toString() {
//        return  "SubTask{" +
//                "name='" + this.getName() + '\'' +
//                ", description='" + this.getDescription() + '\'' +
//                ", id=" + this.getId() +
//                ", status=" + this.getStatus() +
//                ", epic=" + this.epicId +
//                '}';
//    }

    @Override
    public String toString() {
        return this.epicId + ",SUBTASK," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + "," + this.getEpicId();
    }

    public static SubTask fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid format for SubTask string");
        }
        SubTask subTask = new SubTask(parts[2], parts[4], Status.valueOf(parts[3]), Integer.parseInt(parts[5]));
        subTask.setId(Integer.parseInt(parts[0]));
        return subTask;
    }

    public int getEpicId() {
        return epicId;
    }
}
