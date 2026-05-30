package com.logistics.taskflow.service;

import com.logistics.taskflow.dto.TaskCreateDTO;
import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.dto.TaskUpdateDTO;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;

import java.util.List;

/**
 * Service contract for managing logistics tasks in the TaskFlow system.
 * Defines CRUD operations and additional filtering and sorting capabilities.
 */
public interface TaskService {

    /**
     * Creates a new task from the provided data.
     *
     * @param createTaskDTO the task creation payload
     * @return the persisted task as a response DTO
     */
    TaskResponseDTO createTask(TaskCreateDTO createTaskDTO);

    /**
     * Retrieves all tasks, with optional filtering by status and/or priority.
     *
     * @param status   optional filter for task status
     * @param priority optional filter for task priority
     * @return list of matching tasks
     */
    List<TaskResponseDTO> getAllTasks(Status status, Priority priority);

    /**
     * Retrieves a single task by its unique identifier.
     *
     * @param id the task ID
     * @return the task as a response DTO
     * @throws com.logistics.taskflow.exception.ResourceNotFoundException if not found
     */
    TaskResponseDTO getTaskById(Long id);

    /**
     * Updates an existing task's fields.
     *
     * @param id            the ID of the task to update
     * @param updateTaskDTO the updated field values
     * @return the updated task as a response DTO
     * @throws com.logistics.taskflow.exception.ResourceNotFoundException if not found
     */
    TaskResponseDTO updateTask(Long id, TaskUpdateDTO updateTaskDTO);

    /**
     * Deletes a task permanently from the system.
     *
     * @param id the ID of the task to delete
     * @throws com.logistics.taskflow.exception.ResourceNotFoundException if not found
     */
    void deleteTask(Long id);

    /**
     * Retrieves all tasks ordered by priority from highest to lowest.
     *
     * @return list of tasks sorted by priority descending
     */
    List<TaskResponseDTO> getTasksOrderedByPriority();
}
