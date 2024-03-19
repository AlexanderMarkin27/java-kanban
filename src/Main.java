import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.FileBackedTaskManager;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

public class Main {

    public static void main(String[] args) {
        FileBackedTaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager();

        Task task1 = new Task("task 1", "task 1", Status.NEW);
        fileBackedTaskManager.getTaskByIndex(1);

        fileBackedTaskManager.createTask(task1);
    }
}
