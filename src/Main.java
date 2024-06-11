
import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.FileBackedTaskManager;
import com.yandex.taskTracker.service.HistoryManager;
import com.yandex.taskTracker.service.InMemoryTaskManager;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("testFile.csv");

        LocalDateTime dateTime1 = LocalDateTime.parse("2024-06-11T21:15:00");

        LocalDateTime dateTimeSub1 = LocalDateTime.parse("2024-06-11T21:30:00");
        LocalDateTime dateTimeSub2 = LocalDateTime.parse("2024-06-11T21:40:00");

//        File file = File.createTempFile("taskManagerData", ".csv");
//        System.out.println(file.getAbsolutePath());

        HistoryManager manager = Managers.getDefaultHistory();

        FileBackedTaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager(file, manager);
        System.out.println(fileBackedTaskManager.getTasksList());
        System.out.println(fileBackedTaskManager.getSubTasksList());
        System.out.println(fileBackedTaskManager.getEpicsList());

        Task task1 = new Task("Task 1", "task 1", Status.NEW, 5, dateTime1);
        Task task2 = new Task("Task 2", "task 2", Status.IN_PROGRESS, 100, dateTimeSub2);
        Task task3 = new Task("Task 3", "task 3", Status.IN_PROGRESS, 1, dateTimeSub1);
        fileBackedTaskManager.createTask(task1);
        System.out.println(task1.getEndTime());

        Epic epic1 = new Epic("Epic 1", "epic 1");
        fileBackedTaskManager.createEpic(epic1);

        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, epic1.getId(), 5, dateTimeSub2);
        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, epic1.getId(), 5, dateTimeSub1);
        SubTask subTask3 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, epic1.getId(), 5, dateTimeSub1);

        fileBackedTaskManager.createSubTask(subTask1);



        fileBackedTaskManager.createSubTask(subTask2);


        fileBackedTaskManager.createSubTask(subTask3);

        fileBackedTaskManager.createTask(task2);
        fileBackedTaskManager.createTask(task3);


    }
}
