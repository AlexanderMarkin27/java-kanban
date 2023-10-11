import util.Status;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TasksManager manager = new TasksManager();

//        Task task1 = new Task("Task 1", "This is task 1. it has status new");
//        Task task2 = new Task("Task 2", "This is task 2. it has status done");
//        Task task3 = new Task("Task 3", "This is task 3. it has status inProgress");
//        Task taskForUpdate = new Task("Task for update", "This is task for update. it has status inProgress");
//
//        manager.createTask(task1);
//        manager.createTask(task2);
//        manager.createTask(task3);
//
//        System.out.println(manager.getTasksList());
//
//        manager.getTaskByIndex(1);
//        manager.updateTask(1, taskForUpdate);
//
//        System.out.println(manager.getTasksList());
//
//        manager.deleteTask(1);
//
//        System.out.println(manager.getTasksList());
//
//        manager.deleteAllTasks();
//
//        System.out.println(manager.getTasksList());

//        Epic epic = new Epic("Epic 1", "ejhdsalkjhgfsdakhf", Status.NEW, new ArrayList<>());
//
//        manager.createEpic(epic);
//
//        System.out.println(manager.getEpicsList());


        ArrayList<Integer> asi = new ArrayList<>();
        asi.add(3);
        asi.add(4);
        System.out.println(asi);
        asi.remove(Integer.valueOf(9));
        System.out.println(asi);


    }
}
