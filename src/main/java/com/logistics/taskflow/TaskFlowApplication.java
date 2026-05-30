package com.logistics.taskflow;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class TaskFlowApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(TaskFlowApplication.class, args);
    }
}
