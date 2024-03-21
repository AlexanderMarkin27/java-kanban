import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.FileBackedTaskManager;
import com.yandex.taskTracker.service.HistoryManager;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("taskManagerData.csv");
        FileBackedTaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager(file);
        HistoryManager historyManager = Managers.getDefaultHistory();

        fileBackedTaskManager.setHistoryManager(historyManager);
//
//        Task task1 = new Task("task 1", "task 1", Status.NEW);
//
//
//        fileBackedTaskManager.createTask(task1);
//
//        fileBackedTaskManager.getTaskByIndex(1);
//        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());
//
//        Task task2 = new Task("task 2", "task 2", Status.NEW);
//        fileBackedTaskManager.createTask(task2);

        fileBackedTaskManager.loadFromFile();
        System.out.println(fileBackedTaskManager.getTasksList());
        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());

    }
}
