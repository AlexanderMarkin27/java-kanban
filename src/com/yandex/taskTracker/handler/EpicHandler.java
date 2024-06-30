package com.yandex.taskTracker.handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.exception.TaskTimeOverlapException;
import com.yandex.taskTracker.model.Epic;
import com.yandex.taskTracker.service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.yandex.taskTracker.enums.HttpResponseCode.*;
import static com.yandex.taskTracker.enums.HttpResponseCode.NOT_ACCEPTABLE;

public class EpicHandler extends BaseHttpHandler {

    private final TaskManager taskManager;
    private final Gson objectMapper;

    public EpicHandler(TaskManager taskManager, Gson objectMapper) {
        this.taskManager = taskManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        switch (pathParts[pathParts.length - 1]) {
            case "epics" -> handleAllEpicsGetRequest(exchange);
            case "subtasks" -> handleEpicAllSubtasksGetRequest(exchange, pathParts[pathParts.length - 2]);
            default -> handleSingleEpicGetRequest(exchange, pathParts[pathParts.length - 1]);
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try {
            Epic epic = objectMapper.fromJson(body, Epic.class);

            if (epic.getId() != null) {
                try {
                    taskManager.updateEpic(epic);
                    sendResponseCode(exchange, ACCEPTED);
                } catch (TaskTimeOverlapException e) {
                    sendResponseCode(exchange, NOT_ACCEPTABLE);
                }
            } else {
                try {
                    taskManager.createEpic(epic);
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
                taskManager.deleteEpicByIndex(index);
                sendResponseCode(exchange, NO_CONTENT);
            } catch (NumberFormatException e) {
                sendResponseCode(exchange, BAD_REQUEST);
            }
        } else {
            taskManager.deleteAllEpics();
            sendResponseCode(exchange, NO_CONTENT);
        }
    }

    private void handleAllEpicsGetRequest(HttpExchange exchange) throws IOException {
        sendText(exchange, objectMapper.toJson(taskManager.getEpicsList()));
    }

    private void handleSingleEpicGetRequest(HttpExchange exchange, String query) throws IOException {
        try {
            int index = Integer.parseInt(query);
            Epic epic = taskManager.getEpicByIndex(index);
            if (epic != null) {
                sendText(exchange, objectMapper.toJson(epic));
            } else {
                sendResponseCode(exchange, NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }

    private void handleEpicAllSubtasksGetRequest(HttpExchange exchange, String query) throws IOException {
        try {
            int index = Integer.parseInt(query);
            ArrayList<Integer> subTasks = taskManager.getEpicByIndex(index).getSubTasks();
            if (subTasks != null) {
                sendText(exchange, objectMapper.toJson(subTasks));
            } else {
                sendResponseCode(exchange, NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendResponseCode(exchange, BAD_REQUEST);
        }
    }
}