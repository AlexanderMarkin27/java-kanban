package com.yandex.taskTracker.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.enums.HttpMethod;
import com.yandex.taskTracker.enums.HttpResponseCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.taskTracker.enums.HttpMethod;
import com.yandex.taskTracker.enums.HttpResponseCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {
    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendResponseCode(HttpExchange exchange, HttpResponseCode responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode.getCode(), 0);
        exchange.close();
    }

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (HttpMethod.POST.name().equals(requestMethod)) {
            handlePost(exchange);
        } else if (HttpMethod.GET.name().equals(requestMethod)) {
            handleGet(exchange);
        } else if (HttpMethod.DELETE.name().equals(requestMethod)) {
            handleDelete(exchange);
        }
    }

    protected abstract void handleGet(HttpExchange exchange) throws IOException;
    protected abstract void handlePost(HttpExchange exchange) throws IOException;
    protected abstract void handleDelete(HttpExchange exchange) throws IOException;
}
