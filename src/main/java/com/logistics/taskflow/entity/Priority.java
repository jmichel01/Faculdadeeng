package com.logistics.taskflow.entity;

/**
 * Enum representing the urgency level of a task.
 * <ul>
 *   <li>{@code LOW} - Non-critical task, can be addressed later.</li>
 *   <li>{@code MEDIUM} - Standard priority task. Default value.</li>
 *   <li>{@code HIGH} - Urgent task, must be addressed immediately.</li>
 * </ul>
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH
}
