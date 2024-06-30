package com.yandex.taskTracker.enums;

public enum HttpResponseCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),

    NOT_ALLOWED(405, "Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private final int code;
    private final String description;

    HttpResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}
