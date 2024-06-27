package com.yandex.taskTracker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import com.yandex.taskTracker.adapter.DurationAdapter;
import com.yandex.taskTracker.adapter.LocalDateTimeAdapter;
import com.yandex.taskTracker.handler.*;
import com.yandex.taskTracker.service.TaskManager;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    public final Gson objectMapper;
    private static final int PORT = 8080;

    TaskManager taskManager;
    private final HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        httpServer = HttpServer.create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        this.objectMapper = gsonBuilder.create();
    }

    public void start() throws IOException {

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager, objectMapper));
        httpServer.createContext("/subtasks", new SubtaskHandler(taskManager, objectMapper));
        httpServer.createContext("/epics", new EpicHandler(taskManager, objectMapper));
        httpServer.createContext("/history", new HistoryHandler(taskManager, objectMapper));
        httpServer.createContext("/prioritized", new PriorityHandler(taskManager, objectMapper));
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

    public void stop() {
        httpServer.stop(1);
        System.out.println("HTTP-сервер остановлен");
    }
}
