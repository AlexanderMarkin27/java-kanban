package com.yandex.taskTracker.model;

import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.enums.Type;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTasks;
    private Type type;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
        this.type = Type.EPIC;
    }

    @Override
    public String toString() {
        return this.getId() + "," + type + "," + this.getName() + "," + this.getStatus()
                + ","  + this.getDescription();
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
