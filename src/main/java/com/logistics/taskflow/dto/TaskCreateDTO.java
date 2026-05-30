package com.logistics.taskflow.dto;

import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO used for creating a new task via the REST API.
 * Contains JSR-380 validation annotations to enforce input constraints.
 */
public class TaskCreateDTO {

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority = Priority.MEDIUM;

    @NotNull(message = "Status is required")
    private Status status = Status.TODO;

    // Constructors
    public TaskCreateDTO() {}

    public TaskCreateDTO(String title, String description, Priority priority, Status status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
