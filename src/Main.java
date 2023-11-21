import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        /* Tasks */
        Task task1 = new Task("Task 1", "this is task 1", Status.NEW);
        Task task2 = new Task("task 2", "this is task 2", Status.IN_PROGRESS);

        /* Create Tasks */
        int indexTask1 = manager.createTask(task1);
        int indexTask2 = manager.createTask(task2);

        /* Epics */

        Epic epic1 = new Epic("Epic 1", "this is epic 1");
        Epic epic2 = new Epic("Epic 2", "this is epic 2");


        manager.getTaskByIndex(indexTask1);
        manager.getTaskByIndex(indexTask2);
        manager.getTaskByIndex(indexTask1);

        int indexEpic1 = manager.createEpic(epic1);
        int indexEpic2 = manager.createEpic(epic2);

        SubTask subTask1 = new SubTask("subtask 1", "this is subtask 1", Status.NEW, indexEpic1);
        SubTask subTask2 = new SubTask("subtask 2", "this is subtask 2", Status.NEW, indexEpic1);
        SubTask subTask3 = new SubTask("subtask 3", "this is subtask 3", Status.NEW, indexEpic1);

        manager.getEpicByIndex(indexEpic1);
        manager.getEpicByIndex(indexEpic2);

        int indexSubTask1 = manager.createSubTask(subTask1);
        int indexSubTask2 = manager.createSubTask(subTask2);
        int indexSubTask3 = manager.createSubTask(subTask3);


        manager.getSubTaskByIndex(indexSubTask1);
        manager.getSubTaskByIndex(indexSubTask2);
        manager.getSubTaskByIndex(indexSubTask3);

        System.out.println(manager.getHistory());

        System.out.println("Delete Task");
        manager.deleteTaskByIndex(1);

        System.out.println(manager.getHistory());

        manager.deleteEpicByIndex(epic1.getId());

        System.out.println(manager.getHistory());


        manager.deleteTaskByIndex(1);
        System.out.println(manager.getHistory());
    }
}
