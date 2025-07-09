package com.folkadev.folka_tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.folkadev.folka_tasks.domain.entities.TaskPriority;
import com.folkadev.folka_tasks.domain.entities.TaskStatus;

public record TaskDto(
    UUID id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskPriority priority,
    TaskStatus status) {
}
