package com.logistics.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TaskFlowApplication is the main entry point for the Spring Boot task manager application.
 * It bootstrap-loads the full application context and launches the embedded server.
 *
 * <p>Uses {@code @SpringBootApplication} which combines:
 * <ul>
 *   <li>{@code @SpringBootConfiguration} - marks this as a configuration class</li>
 *   <li>{@code @EnableAutoConfiguration} - enables Spring Boot auto-configuration</li>
 *   <li>{@code @ComponentScan} - scans this package and subpackages for components</li>
 * </ul>
 */
@SpringBootApplication
public class TaskFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskFlowApplication.class, args);
    }
}
