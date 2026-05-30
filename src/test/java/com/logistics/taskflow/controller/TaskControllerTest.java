package com.logistics.taskflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.taskflow.dto.TaskCreateDTO;
import com.logistics.taskflow.dto.TaskResponseDTO;
import com.logistics.taskflow.dto.TaskUpdateDTO;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import com.logistics.taskflow.exception.ResourceNotFoundException;
import com.logistics.taskflow.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private TaskResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new TaskResponseDTO(
                1L,
                "Ship Container B",
                "Ship to warehouse East",
                Priority.MEDIUM,
                Status.TODO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("POST /api/v1/tasks should return 201 Created with valid payload")
    void createTask_WithValidPayload_ShouldReturn201() throws Exception {
        // Arrange
        TaskCreateDTO createDTO = new TaskCreateDTO("Ship Container B", "Ship to warehouse East", Priority.MEDIUM, Status.TODO);
        when(taskService.createTask(any(TaskCreateDTO.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Ship Container B"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    @DisplayName("POST /api/v1/tasks should return 400 Bad Request when title is blank")
    void createTask_WithBlankTitle_ShouldReturn400() throws Exception {
        // Arrange
        TaskCreateDTO createDTO = new TaskCreateDTO("", "Ship to warehouse East", Priority.MEDIUM, Status.TODO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.details", containsString("Title is mandatory")));

        verify(taskService, never()).createTask(any());
    }

    @Test
    @DisplayName("GET /api/v1/tasks/{id} should return 200 OK when exists")
    void getTaskById_WhenExists_ShouldReturn200() throws Exception {
        // Arrange
        when(taskService.getTaskById(1L)).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Ship Container B"));
    }

    @Test
    @DisplayName("GET /api/v1/tasks/{id} should return 404 Not Found when resource does not exist")
    void getTaskById_WhenNotExists_ShouldReturn404() throws Exception {
        // Arrange
        when(taskService.getTaskById(99L)).thenThrow(new ResourceNotFoundException("Task not found with id: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: 99"));
    }

    @Test
    @DisplayName("PUT /api/v1/tasks/{id} should return 200 OK with updated task details")
    void updateTask_ShouldReturn200() throws Exception {
        // Arrange
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("Ship Container B Updated", "Ship to warehouse East", Priority.HIGH, Status.IN_PROGRESS);
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
                1L,
                "Ship Container B Updated",
                "Ship to warehouse East",
                Priority.HIGH,
                Status.IN_PROGRESS,
                responseDTO.getCreatedAt(),
                LocalDateTime.now()
        );
        when(taskService.updateTask(eq(1L), any(TaskUpdateDTO.class))).thenReturn(updatedResponse);

        // Act & Assert
        mockMvc.perform(put("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Ship Container B Updated"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    @DisplayName("DELETE /api/v1/tasks/{id} should return 204 No Content")
    void deleteTask_ShouldReturn204() throws Exception {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }
}
