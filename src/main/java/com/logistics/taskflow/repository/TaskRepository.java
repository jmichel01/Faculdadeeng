package com.logistics.taskflow.repository;

import com.logistics.taskflow.entity.Task;
import com.logistics.taskflow.entity.Priority;
import com.logistics.taskflow.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Task} persistence operations.
 * Extends Spring Data JPA's {@code JpaRepository} to provide standard CRUD methods
 * and declares custom query methods for filtering and ordering tasks.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /** Finds all tasks matching a given status. */
    List<Task> findByStatus(Status status);

    /** Finds all tasks matching a given priority level. */
    List<Task> findByPriority(Priority priority);

    /** Finds all tasks matching both a given status and priority level. */
    List<Task> findByStatusAndPriority(Status status, Priority priority);

    /** Finds all tasks ordered by priority in descending order (HIGH first). */
    List<Task> findAllByOrderByPriorityDesc();

    /** Finds all tasks whose title contains the given keyword (case-insensitive). */
    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
