import util.Status;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTasks;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.subTasks = new ArrayList<>();
    }

//    @Override
//    public String toString() {
//        return "Task{" +
//                "name='" +  + '\'' +
//                ", description='" + description + '\'' +
//                ", id=" + id +
//                ", status=" + status +
//                '}';
//    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
}
