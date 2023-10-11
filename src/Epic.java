import util.Status;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTasks;

    public Epic(String name, String description, Status status, ArrayList<Integer> subTasks) {
        super(name, description, status);
        this.subTasks = subTasks;
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
