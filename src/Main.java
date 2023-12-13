import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.FileBackedTasksManager;
import com.yandex.taskTracker.service.HistoryManager;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
       File file = new File("./resources/tasks.csv");
       FileBackedTasksManager manager = Managers.getFileBackedTasksManager(file);
        HistoryManager manager1 = Managers.getDefaultHistory();

        Task task1 = new Task("Task 1", "this is task 1", Status.NEW);
        Task task2 = new Task("task 2", "this is task 2", Status.IN_PROGRESS);

        int taskId1 = manager.createTask(task1);
        int taskId2 = manager.createTask(task2);

        manager.getTaskByIndex(taskId1);
        manager.getTaskByIndex(taskId2);
        int taskIdUpdataed = manager.updateTask(task2);

    }


}
