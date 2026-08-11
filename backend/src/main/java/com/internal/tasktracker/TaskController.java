package com.internal.tasktracker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/tasks")
    public ResponseEntity<?> searchTasks(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        // Normalize query input
        String query = q == null ? "" : q.trim();
        String searchTerm = "%" + query.toLowerCase() + "%";

        // Parse status filter
        String normalizedStatus = null;
        if (status != null && !status.isEmpty()) {
            TaskStatus parsedStatus = TaskStatus.fromStringIgnoreCase(status);
            if (parsedStatus != null) {
                normalizedStatus = parsedStatus.name();
            }
        }

        logger.info("searchTasks called: q=\"{}\" status={} page={} pageSize={}", 
                query, normalizedStatus, page, pageSize);

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Task> taskPage = taskRepository.searchTasks(searchTerm, normalizedStatus, pageRequest);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("items", taskPage.getContent());
        response.put("total", taskPage.getTotalElements());
        response.put("page", page);
        response.put("pageSize", pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        task.setCreatedAt(java.time.LocalDateTime.now());
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.OPEN.name());
        }
        task.setArchived(false);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }
}
