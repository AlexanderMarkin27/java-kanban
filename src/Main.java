import com.yandex.taskTracker.server.HttpTaskServer;
import com.yandex.taskTracker.service.TaskManager;
import com.yandex.taskTracker.utils.Managers;
import java.io.IOException;
import java.time.Duration;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();

    }
}
