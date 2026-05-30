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

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskCreateDTO createTaskDTO) {
        TaskResponseDTO createdTask = taskService.createTask(createTaskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority) {
        List<TaskResponseDTO> tasks = taskService.getAllTasks(status, priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDTO updateTaskDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, updateTaskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
