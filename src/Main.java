import util.Status;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TasksManager manager = new TasksManager();

//        System.out.println("---------------------------------- Task -----------------------------------------");
//
//        manager.createTask(new Task("Task 1", "this is task number 1", Status.NEW));
//        manager.createTask(new Task("Task 2", "this is task number 2", Status.NEW));
//
//        System.out.println("Print list with 2 tasks");
//        System.out.println(manager.getTasksList());
//
//        System.out.println("Print task with id 1");
//        System.out.println(manager.getTaskByIndex(1));
//
//        System.out.println("Print updated task. Name, description and status should be changed, id 1");
//        System.out.println(manager.updateTask(1, new Task("Updated task", "this is updated",
//                Status.IN_PROGRESS)));
//
//        System.out.println("Print list only with 1 task, id 1");
//        System.out.println(manager.deleteTaskByIndex(0));
//
//        System.out.println("Print empty list");
//        System.out.println(manager.deleteAllTasks());

        System.out.println("---------------------------------- Epic + Subtasks --------------------------------");

        manager.createEpic(new Epic("Epic 1", "This is epic 1", Status.NEW));
        manager.createEpic(new Epic("Epic 12", "This is epic 12", Status.NEW));

        System.out.println(manager.getEpicsList());

        manager.createSubTask(new SubTask("Subtask 1", "this is subtask 1", Status.NEW, 0));

    }
}
