import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        /* Tasks */
        Task taskForDelete = new Task("Task for delete", "this is task for delete", Status.NEW);
        Task taskForUpdate = new Task("task for update", "this is task for update", Status.NEW);
        Task updatedTask = new Task("updated Task", "this is updated Task", Status.IN_PROGRESS);

        int indexTaskForDelete = manager.createTask(taskForDelete);
        int indexTask4Update = manager.createTask(taskForUpdate);

        System.out.println(manager.getTasksList());

        updatedTask.setId(indexTask4Update);

        int indexUpdatedTask = manager.updateTask(updatedTask);
        System.out.println(manager.getTaskByIndex(indexUpdatedTask));

        manager.deleteTaskByIndex(indexTaskForDelete);

        System.out.println(manager.getTaskByIndex(indexTaskForDelete));
        System.out.println(manager.getTasksList());

        manager.deleteAllTasks();
        System.out.println(manager.getTasksList());

        /* Epics + SubTasks */

        Epic epicForDelete = new Epic("Epic for Delete", "this is epic for delete");
        Epic epicForUpdate = new Epic("epic for Update", "this is epic for update");
        Epic updatedEpic = new Epic("updated epic", "this is updated epic");

        int indexEpicForDelete = manager.createEpic(epicForDelete);
        int indexEpicForUpdate = manager.createEpic(epicForUpdate);

        SubTask subTask1 = new SubTask("subtask 1", "this is subtask 1", Status.NEW, indexEpicForUpdate);
        SubTask subTask2 = new SubTask("subtask 2", "this is subtask 2", Status.NEW, indexEpicForUpdate);

        SubTask subTaskForDelete = new SubTask("subtask for delete", "this is subtask for delete", Status.NEW, indexEpicForDelete);
        SubTask subTaskForDelete2 = new SubTask("subtask for delete2", "this is subtask for delete2", Status.DONE, indexEpicForDelete);

        SubTask updatedSubTask = new SubTask("updated Subtask", "this is updated subtask", Status.IN_PROGRESS, indexEpicForUpdate);

        int indexSubTask1 = manager.createSubTask(subTask1);
        int indexSubTask2 = manager.createSubTask(subTask2);

        System.out.println(manager.getEpicByIndex(indexEpicForUpdate));

        updatedSubTask.setId(indexSubTask1);

        manager.updateSubTask(updatedSubTask);

        System.out.println(manager.getEpicByIndex(indexEpicForUpdate));

        SubTask updatedSubTask2 = new SubTask("updated Subtask2", "this is updated Subtask 2", Status.DONE, indexEpicForUpdate);
        SubTask updatedSubTask3 = new SubTask("updated Subtask3", "this is updated Subtask 3", Status.DONE, indexEpicForUpdate);

        updatedSubTask2.setId(indexSubTask1);
        updatedSubTask3.setId(indexSubTask2);

        int indexUpdatedSubTask2 = manager.updateSubTask(updatedSubTask2);
        int indexUpdatedSubTask3 = manager.updateSubTask(updatedSubTask3);

        System.out.println(manager.getSubTaskByIndex(indexUpdatedSubTask2));
        System.out.println(manager.getSubTaskByIndex(indexUpdatedSubTask3));

        System.out.println(manager.getEpicByIndex(indexEpicForUpdate));

        updatedEpic.setId(indexEpicForUpdate);

        manager.updateEpic(updatedEpic);

        System.out.println(manager.getEpicByIndex(indexEpicForUpdate));

        manager.createSubTask(subTaskForDelete);
        int indexSubTaskForDelete = manager.createSubTask(subTaskForDelete2);

        System.out.println(manager.getEpicsList());

        manager.deleteSubTaskByIndex(indexSubTaskForDelete);

        System.out.println(manager.getEpicsList());

        manager.deleteEpicByIndex(indexEpicForDelete);

        System.out.println(manager.getEpicsList());
        System.out.println(manager.getSubTasksList());

        System.out.println(manager.getSubTasksByEpic(indexEpicForUpdate));

        manager.deleteAllSubTasks();

        System.out.println(manager.getEpicByIndex(indexEpicForUpdate));

        manager.deleteAllEpics();

        System.out.println(manager.getSubTasksList());
        System.out.println(manager.getEpicsList());

        System.out.println(manager.getHistory());



    }
}
