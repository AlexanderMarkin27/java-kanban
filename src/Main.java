import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.service.InMemoryTaskManager;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        TaskManager fileBackedTaskManager = Managers.getDefault();

        Epic epic1 = new Epic("Epic 1", "epic 1");
        fileBackedTaskManager.createEpic(epic1);

        SubTask subTask1 = new SubTask("SubTask 1", "SubTask 1", Status.NEW, epic1.getId(), 15, LocalDateTime.now());
        SubTask subTask2 = new SubTask("SubTask 2", "SubTask 2", Status.NEW, epic1.getId(), 15, LocalDateTime.now().plusMinutes(17));

        fileBackedTaskManager.createSubTask(subTask1);
        fileBackedTaskManager.createSubTask(subTask2);

        System.out.println(fileBackedTaskManager.getSubTasksList());
        System.out.println(fileBackedTaskManager.getEpicsList());

    }
}
