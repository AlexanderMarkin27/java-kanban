package com.yandex.taskTracker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yandex.taskTracker.adapter.DurationAdapter;
import com.yandex.taskTracker.adapter.LocalDateTimeAdapter;
import com.yandex.taskTracker.enums.Status;
import com.yandex.taskTracker.model.Epic;
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

public class HttpTaskManagerEpicsTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson;

    public HttpTaskManagerEpicsTest() throws IOException {
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
    public void testAddEpic_epicCreated() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 1", "Testing epic 1");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Epic> epicsFromManager = manager.getEpicsList();

        assertNotNull(epicsFromManager, "Эпики не возвращаются");
        assertEquals(1, epicsFromManager.size(), "Некорректное количество эпиков");
        assertEquals("Epic 1", epicsFromManager.get(0).getName(), "Некорректное имя эпика");
    }

    @Test
    public void testUpdateEpic_epicUpdated() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 2", "Testing epic 2");
        Epic epicUpdated = new Epic("Test Updated", "Testing epic 2");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicsFromManager = manager.getEpicsList();
        epicUpdated.setId(epicsFromManager.get(0).getId());

        String epicUpdatedJson = gson.toJson(epicUpdated);
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicUpdatedJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(202, response.statusCode());

        epicsFromManager = manager.getEpicsList();

        assertNotNull(epicsFromManager, "Эпики не возвращаются");
        assertEquals(1, epicsFromManager.size(), "Некорректное количество эпиков");
        assertEquals("Test Updated", epicsFromManager.get(0).getName(), "Некорректное имя эпика");
    }

    @Test
    public void testDeleteEpic_epicDeleted() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 1", "Testing epic 1");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicsFromManager = manager.getEpicsList();
        Integer epicId = epicsFromManager.get(0).getId();
        url = URI.create("http://localhost:8080/epics/" + epicId);;

        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, response.statusCode());

        epicsFromManager = manager.getEpicsList();

        assertEquals(0, epicsFromManager.size(), "Некорректное количество эпиков");
    }
}
