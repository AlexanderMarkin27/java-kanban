package com.yandex.taskTracker.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.service.TaskManager;

import java.io.IOException;

import static com.yandex.taskTracker.enums.HttpResponseCode.BAD_REQUEST;
import static com.yandex.taskTracker.enums.HttpResponseCode.NOT_ALLOWED;

public class PriorityHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson objectMapper;

    public PriorityHandler(TaskManager taskManager, Gson objectMapper) {
        this.taskManager = taskManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts[pathParts.length - 1].equals("prioritized")) {
            sendText(exchange, objectMapper.toJson(taskManager.getPrioritizedTasks()));
        } else {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        sendResponseCode(exchange, NOT_ALLOWED);
    }

    @Override
    protected void handleDelete(HttpExchange exchange) throws IOException {
        sendResponseCode(exchange, NOT_ALLOWED);
    }
}