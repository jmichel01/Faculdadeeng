package com.logistics.taskflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found in the database.
 * Maps automatically to HTTP 404 Not Found responses.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the given message.
     *
     * @param message human-readable description of what was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience factory method for building task-specific not-found exceptions.
     *
     * @param taskId the ID of the missing task
     * @return a new {@link ResourceNotFoundException} with a descriptive message
     */
    public static ResourceNotFoundException forTask(Long taskId) {
        return new ResourceNotFoundException("Task not found with id: " + taskId);
    }
}
