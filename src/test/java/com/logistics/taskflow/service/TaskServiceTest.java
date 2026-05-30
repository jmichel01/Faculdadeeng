package com.logistics.taskflow.service;

import com.logistics.taskflow.dto.TaskCreateDTO;
import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.dto.TaskUpdateDTO;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import com.logistics.taskflow.entity.Task;
import com.logistics.taskflow.exception.ResourceNotFoundException;
import com.logistics.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Deliver Cargo A", "Route 66 delivery", Priority.HIGH, Status.TODO);
        sampleTask.setId(1L);
    }

    @Test
    @DisplayName("Create Task should save and return TaskResponseDTO")
    void createTask_ShouldSaveAndReturnResponse() {
        // Arrange
        TaskCreateDTO createDTO = new TaskCreateDTO("Deliver Cargo A", "Route 66 delivery", Priority.HIGH, Status.TODO);
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // Act
        TaskResponseDTO result = taskService.createTask(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleTask.getId(), result.getId());
        assertEquals(sampleTask.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Get Task By Id should return DTO when task exists")
    void getTaskById_WhenExists_ShouldReturnDTO() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // Act
        TaskResponseDTO result = taskService.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get Task By Id should throw ResourceNotFoundException when task does not exist")
    void getTaskById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Update Task should update fields and save")
    void updateTask_ShouldUpdateFieldsAndSave() {
        // Arrange
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("Deliver Cargo B", "Updated route delivery", Priority.LOW, Status.IN_PROGRESS);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponseDTO result = taskService.updateTask(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Deliver Cargo B", result.getTitle());
        assertEquals("Updated route delivery", result.getDescription());
        assertEquals(Priority.LOW, result.getPriority());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
        verify(taskRepository, times(1)).save(sampleTask);
    }

    @Test
    @DisplayName("Delete Task should delete when task exists")
    void deleteTask_WhenExists_ShouldCallDelete() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).delete(sampleTask);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).delete(sampleTask);
    }

    @Test
    @DisplayName("Delete Task should throw exception when task does not exist")
    void deleteTask_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(99L));
        verify(taskRepository, never()).delete(any(Task.class));
    }
}
