package com.yandex.taskTracker.handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.exception.TaskTimeOverlapException;
import com.yandex.taskTracker.model.SubTask;
import com.yandex.taskTracker.model.Task;
import com.yandex.taskTracker.service.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;

import static com.yandex.taskTracker.enums.HttpResponseCode.*;
import static com.yandex.taskTracker.enums.HttpResponseCode.NOT_ACCEPTABLE;

public class SubtaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson objectMapper;

    public SubtaskHandler(TaskManager taskManager, Gson objectMapper) {
        this.taskManager = taskManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts.length > 2) {
            handleSingleSubTaskGetRequest(exchange, pathParts[2]);
        } else {
            handleAllSubTasksGetRequest(exchange);
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        try {
            SubTask subTask = objectMapper.fromJson(new InputStreamReader(exchange.getRequestBody()), SubTask.class);

            if (subTask.getId() != null) {
                try {
                    taskManager.updateSubTask(subTask);
                    sendResponseCode(exchange, ACCEPTED);
                } catch (TaskTimeOverlapException e) {
                    sendResponseCode(exchange, NOT_ACCEPTABLE);
                }
            } else {
                try {
                    taskManager.createSubTask(subTask);
                    sendResponseCode(exchange, CREATED);
                } catch (TaskTimeOverlapException e) {
                    sendResponseCode(exchange, NOT_ACCEPTABLE);
                }
            }

        } catch (JsonParseException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }

    @Override
    protected void handleDelete(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length > 2) {
            try {
                int index = Integer.parseInt(pathParts[2]);
                taskManager.deleteSubTaskByIndex(index);
                sendResponseCode(exchange, NO_CONTENT);
            } catch (NumberFormatException e) {
                sendResponseCode(exchange, BAD_REQUEST);
            }
        } else {
            taskManager.deleteAllSubTasks();
            sendResponseCode(exchange, NO_CONTENT);
        }
    }

    private void handleAllSubTasksGetRequest(HttpExchange exchange) throws IOException {
        sendText(exchange, objectMapper.toJson(taskManager.getSubTasksList()));
    }

    private void handleSingleSubTaskGetRequest(HttpExchange exchange, String query) throws IOException {
        try {
            int index = Integer.parseInt(query);
            Task subTask = taskManager.getSubTaskByIndex(index);
            if (subTask != null) {
                sendText(exchange, objectMapper.toJson(subTask));
            } else {
                sendResponseCode(exchange, NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }
}
