package com.yandex.taskTracker.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.yandex.taskTracker.handler.EpicHandler;
import com.yandex.taskTracker.handler.SubtaskHandler;
import com.yandex.taskTracker.handler.TaskHandler;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.utils.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final Gson OBJECT_MAPPER = new Gson();
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(Managers.getDefault(), OBJECT_MAPPER));
        httpServer.createContext("/subtasks", new SubtaskHandler(Managers.getDefault(), OBJECT_MAPPER));
        httpServer.createContext("/epics", new EpicHandler(Managers.getDefault(), OBJECT_MAPPER));

        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }
}
