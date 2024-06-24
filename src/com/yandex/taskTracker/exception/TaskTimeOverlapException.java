package com.yandex.taskTracker.exception;

public class TaskTimeOverlapException extends RuntimeException {
    public TaskTimeOverlapException() {
        }

    public TaskTimeOverlapException(final String message) {
            super(message);
        }

    public TaskTimeOverlapException(final String message, final Throwable cause) {
            super(message, cause);
        }

    public TaskTimeOverlapException(final Throwable cause) {
            super(cause);
        }
}
