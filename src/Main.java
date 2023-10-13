import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.TasksManager;

public class Main {

    public static void main(String[] args) {
        TasksManager manager = new TasksManager();

        System.out.println("---------------------------------- Task -----------------------------------------");

        manager.createTask(new Task("com.yandex.taskTracker.model.Task 1", "this is task number 1", Status.NEW));
        manager.createTask(new Task("com.yandex.taskTracker.model.Task 2", "this is task number 2", Status.NEW));

        System.out.println(manager.getTasksList());

        System.out.println(manager.getTaskByIndex(1));

        System.out.println(manager.updateTask(1, new Task("Updated task", "this is updated",
                Status.IN_PROGRESS)));

        System.out.println(manager.deleteTaskByIndex(0));

        System.out.println(manager.deleteAllTasks());

        System.out.println("---------------------------------- Subtasks --------------------------------");

        manager.createEpic(new Epic("com.yandex.taskTracker.model.Epic 1", "This is epic 1", Status.NEW));
        manager.createEpic(new Epic("com.yandex.taskTracker.model.Epic 12", "This is epic 12", Status.NEW));



        manager.createSubTask(new SubTask("Subtask 1", "this is subtask 1", Status.NEW, 0));
        manager.createSubTask(new SubTask("Subtask 2", "this is subtask 2", Status.NEW, 0));
        manager.createSubTask(new SubTask("Subtask 3", "this is subtask 3", Status.IN_PROGRESS, 1));

        System.out.println(manager.getSubTasksList());
        System.out.println(manager.getEpicsList());

        manager.updateSubTask(3, new SubTask("Subtask 1", "this is subtask 1", Status.IN_PROGRESS, 0));
        manager.updateSubTask(4, new SubTask("Subtask 51", "subtask done", Status.DONE, 1));

        System.out.println(manager.getSubTasksList());
        System.out.println(manager.getEpicsList());

        System.out.println(manager.getSubTaskByIndex(4));
        System.out.println(manager.getEpicByIndex(0));

        manager.deleteEpicByIndex(1);

        System.out.println(manager.getSubTasksList());
        System.out.println(manager.getEpicsList());

        manager.deleteSubTaskByIndex(3);

        System.out.println(manager.getSubTasksList());
        System.out.println(manager.getEpicsList());

        System.out.println(manager.getSubTasksByEpic(0));

        System.out.println(manager.deleteAllSubTasks());
        System.out.println(manager.getEpicsList());


        System.out.println( manager.deleteAllEpics());
    }
}
