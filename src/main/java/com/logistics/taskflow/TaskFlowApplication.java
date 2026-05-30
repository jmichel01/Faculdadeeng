package com.logistics.taskflow;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * TaskFlowApplication is the main entry point for the Spring Boot task manager application.
 * It bootstrap-loads the full application context and launches the embedded server.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class TaskFlowApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(TaskFlowApplication.class, args);
    }
}
