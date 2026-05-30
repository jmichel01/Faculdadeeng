package com.logistics.taskflow.service;

import com.logistics.taskflow.dto.TaskCreateDTO;
import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.dto.TaskUpdateDTO;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;

import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskCreateDTO createTaskDTO);
    List<TaskResponseDTO> getAllTasks(Status status, Priority priority);
    TaskResponseDTO getTaskById(Long id);
    TaskResponseDTO updateTask(Long id, TaskUpdateDTO updateTaskDTO);
    void deleteTask(Long id);
    List<TaskResponseDTO> getTasksOrderedByPriority();
}
