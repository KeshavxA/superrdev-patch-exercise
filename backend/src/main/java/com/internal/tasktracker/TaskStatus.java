package com.internal.tasktracker;

public enum TaskStatus {
    OPEN,
    IN_PROGRESS,
    DONE;

    public static TaskStatus fromStringIgnoreCase(String text) {
        if (text == null) {
            return null;
        }
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
