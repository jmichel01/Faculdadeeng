package com.logistics.taskflow.controller;

import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.entity.Task;
import com.logistics.taskflow.exception.ResourceNotFoundException;
import com.logistics.taskflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller dedicated to search operations over tasks.
 * Exposes endpoints to search tasks by title keyword.
 */
@RestController
@RequestMapping("/api/v1/tasks/search")
public class TaskSearchController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskSearchController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Searches tasks whose title contains the given keyword (case-insensitive).
     *
     * @param keyword part of the task title to search for
     * @return list of matching tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> searchByTitle(@RequestParam String keyword) {
        List<Task> results = taskRepository.findByTitleContainingIgnoreCase(keyword);
        List<TaskResponseDTO> dtos = results.stream().map(task -> new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
