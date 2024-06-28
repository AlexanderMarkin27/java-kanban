package com.yandex.taskTracker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yandex.taskTracker.adapter.DurationAdapter;
import com.yandex.taskTracker.adapter.LocalDateTimeAdapter;
import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.InMemoryTaskManager;
import com.yandex.taskTracker.service.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerSubTasksTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson;

    public HttpTaskManagerSubTasksTest() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gson = gsonBuilder.create();
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.deleteAllTasks();
        manager.deleteAllSubTasks();
        manager.deleteAllEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddSubTask_subTaskCreated() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 2", "Testing epic 2");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicsFromManager = manager.getEpicsList();
        Integer epicId = epicsFromManager.get(0).getId();

        SubTask subTask = new SubTask("Test Subtask", "Testing Subtask 2", Status.NEW, epicId, 5, LocalDateTime.now());
        String subTaskJson = gson.toJson(subTask);

        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/subtasks");
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> subTasksFromManager = manager.getSubTasksList();

        assertNotNull(subTasksFromManager, "Подзадачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество подзадач");
        assertEquals("Test Subtask", subTasksFromManager.get(0).getName(), "Некорректное имя подзадачи");
    }

    @Test
    public void testUpdateSubTask_subTaskUpdated() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 2", "Testing epic 2");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicsFromManager = manager.getEpicsList();
        Integer epicId = epicsFromManager.get(0).getId();

        SubTask subTask = new SubTask("Test Subtask", "Testing Subtask 2", Status.NEW, epicId, 5, LocalDateTime.now());
        SubTask subTaskUpdated = new SubTask("Test Subtask update", "Testing Subtask 2", Status.NEW, epicId, 5, LocalDateTime.now());
        String subTaskJson = gson.toJson(subTask);

        url = URI.create("http://localhost:8080/subtasks");
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<SubTask> subTasksFromManager = manager.getSubTasksList();
        subTaskUpdated.setId(subTasksFromManager.get(0).getId());

        String subTaskUpdatedJson = gson.toJson(subTaskUpdated);
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskUpdatedJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(202, response.statusCode());

        subTasksFromManager = manager.getSubTasksList();

        assertNotNull(subTasksFromManager, "Подзадачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество подзадач");
        assertEquals("Test Subtask update", subTasksFromManager.get(0).getName(), "Некорректное имя подзадачи");
    }

    @Test
    public void testDeleteTask_taskDeleted() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 2", "Testing epic 2");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicsFromManager = manager.getEpicsList();
        Integer epicId = epicsFromManager.get(0).getId();

        SubTask subTask = new SubTask("Test Subtask", "Testing Subtask 2", Status.NEW, epicId, 5, LocalDateTime.now());
        String subTaskJson = gson.toJson(subTask);

        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/subtasks");
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<SubTask> subTasksFromManager = manager.getSubTasksList();
        Integer subTaskId = subTasksFromManager.get(0).getId();
        url = URI.create("http://localhost:8080/subtasks/" + subTaskId);;

        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, response.statusCode());

        subTasksFromManager = manager.getSubTasksList();

        assertEquals(0, subTasksFromManager.size(), "Некорректное количество подзадач");
    }
}