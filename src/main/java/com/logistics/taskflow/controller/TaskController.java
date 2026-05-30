package com.logistics.taskflow.controller;

import com.logistics.taskflow.dto.TaskCreateDTO;
import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.dto.TaskUpdateDTO;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import com.logistics.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Task resources.
 * Exposes CRUD operations and filtering endpoints under {@code /api/v1/tasks}.
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     * POST /api/v1/tasks
     */
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskCreateDTO createTaskDTO) {
        TaskResponseDTO createdTask = taskService.createTask(createTaskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Returns all tasks with optional filters by status and/or priority.
     * GET /api/v1/tasks?status=TODO&priority=HIGH
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority) {
        List<TaskResponseDTO> tasks = taskService.getAllTasks(status, priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Returns all tasks ordered from highest to lowest priority.
     * GET /api/v1/tasks/priority
     */
    @GetMapping("/priority")
    public ResponseEntity<List<TaskResponseDTO>> getTasksOrderedByPriority() {
        List<TaskResponseDTO> tasks = taskService.getTasksOrderedByPriority();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Returns a single task by its unique ID.
     * GET /api/v1/tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Updates a task's fields.
     * PUT /api/v1/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDTO updateTaskDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, updateTaskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a task permanently.
     * DELETE /api/v1/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
