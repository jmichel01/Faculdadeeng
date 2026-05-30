package com.logistics.taskflow.repository;

import com.logistics.taskflow.entity.Task;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatusAndPriority(Status status, Priority priority);
    List<Task> findAllByOrderByPriorityDesc();
}
