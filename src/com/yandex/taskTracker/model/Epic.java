package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;
import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
    }

//    @Override
//    public String toString() {
//        return  "Epic{" +
//                "name='" + this.getName() + '\'' +
//                ", description='" + this.getDescription() + '\'' +
//                ", id=" + this.getId() +
//                ", status=" + this.getStatus() +
//                ", subTasks=" + this.subTasks +
//                '}';
//    }

    @Override
    public String toString() {
        return this.getId() + ",EPIC," + this.getName() + "," + this.getStatus() + "," + this.getDescription();
    }
    public static Epic fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid format for Epic string");
        }
        Epic epic = new Epic(parts[2],parts[4]);
        epic.setId(Integer.parseInt(parts[0]));
        epic.setName(parts[2]);
        epic.setStatus(Status.valueOf(parts[3]));
        return epic;
    }
    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
