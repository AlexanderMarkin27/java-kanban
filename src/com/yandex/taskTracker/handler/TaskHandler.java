package com.yandex.taskTracker.handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.exception.TaskTimeOverlapException;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;

import static com.yandex.taskTracker.enums.HttpResponseCode.*;


public class TaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson objectMapper;

    public TaskHandler(TaskManager taskManager, Gson objectMapper) {
        this.taskManager = taskManager;
        this.objectMapper = objectMapper;
    }

    protected void handleGet(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts.length > 1) {
            handleSingleTaskGetRequest(exchange, pathParts[1]);
        } else {
            handleAllTasksGetRequest(exchange);
        }
    }

    protected void handlePost(HttpExchange exchange) throws IOException {
        try {
            Task task = objectMapper.fromJson(new InputStreamReader(exchange.getRequestBody()), Task.class);

            if (task.getId() != null) {
                try {
                    taskManager.updateTask(task);
                    sendResponseCode(exchange, ACCEPTED);
                } catch (TaskTimeOverlapException e) {
                    sendResponseCode(exchange, NOT_ACCEPTABLE);
                }
            } else {
                try {
                    taskManager.createTask(task);
                    sendResponseCode(exchange, CREATED);
                } catch (TaskTimeOverlapException e) {
                    sendResponseCode(exchange, NOT_ACCEPTABLE);
                }
            }

        } catch (JsonParseException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }

    protected void handleDelete(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts.length > 1) {
            try {
                int index = Integer.parseInt(pathParts[1]);
                taskManager.deleteTaskByIndex(index);
                sendResponseCode(exchange, NO_CONTENT);
            } catch (NumberFormatException e) {
                sendResponseCode(exchange, BAD_REQUEST);
            }
        } else {
            taskManager.deleteAllTasks();
            sendResponseCode(exchange, NO_CONTENT);
        }
    }

    private void handleAllTasksGetRequest(HttpExchange exchange) throws IOException {
        sendText(exchange, objectMapper.toJson(taskManager.getTasksList()));
    }

    private void handleSingleTaskGetRequest(HttpExchange exchange, String query) throws IOException {
        try {
            int index = Integer.parseInt(query);
            Task task = taskManager.getTaskByIndex(index);
            if (task != null) {
                sendText(exchange, objectMapper.toJson(task));
            } else {
                sendResponseCode(exchange, NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }
}
