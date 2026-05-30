package com.logistics.taskflow.entity;

/**
 * Enum representing the lifecycle status of a task.
 * <ul>
 *   <li>{@code TODO} - Task has not been started yet.</li>
 *   <li>{@code IN_PROGRESS} - Task is currently being worked on.</li>
 *   <li>{@code DONE} - Task has been completed.</li>
 * </ul>
 */
public enum Status {
    TODO,
    IN_PROGRESS,
    DONE
}
