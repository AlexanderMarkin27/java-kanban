
import com.yandex.taskTracker.enums.Status;
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

        HistoryManager manager = Managers.getDefaultHistory();

        FileBackedTaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager(file, manager);

    }
}
