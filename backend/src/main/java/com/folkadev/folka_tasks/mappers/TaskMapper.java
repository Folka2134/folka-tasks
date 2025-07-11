package com.folkadev.folka_tasks.mappers;

import com.folkadev.folka_tasks.domain.dto.TaskDto;
import com.folkadev.folka_tasks.domain.entities.Task;

public interface TaskMapper {

  Task fromDto(TaskDto taskDto);

  TaskDto toDto(Task task);
}
