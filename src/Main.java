
import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.FileBackedTaskManager;
import com.yandex.taskTracker.service.HistoryManager;
import com.yandex.taskTracker.utils.Managers;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        File file = File.createTempFile("taskManagerData", ".csv");
//        System.out.println(file.getAbsolutePath());
//
//        HistoryManager manager = Managers.getDefaultHistory();
//
//        FileBackedTaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager(file, manager);
//
//        Task task1 = new Task("Task 1", "task 1", Status.NEW);
//        Task task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS);
//        Task task3 = new Task("Task 3", "task 3", Status.IN_PROGRESS);
//        fileBackedTaskManager.createTask(task1);
//        fileBackedTaskManager.createTask(task2);
//        fileBackedTaskManager.createTask(task3);
//        Epic epic1 = new Epic("Epic 1", "epic 1");
//        fileBackedTaskManager.createEpic(epic1);
//
//        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, epic1.getId());
//        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, epic1.getId());
//
//        fileBackedTaskManager.createSubTask(subTask1);
//        fileBackedTaskManager.createSubTask(subTask2);
//
//        fileBackedTaskManager.deleteTaskByIndex(3);
//
//        System.out.println(fileBackedTaskManager.getTasksList());
//        System.out.println(fileBackedTaskManager.getSubTasksList());
//        System.out.println(fileBackedTaskManager.getEpicsList());
//
//
//        Task taskwithId8 = new Task("Task 8", "task 8", Status.NEW);
//        fileBackedTaskManager.createTask(taskwithId8);
    }
}
