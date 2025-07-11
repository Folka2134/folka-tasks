package com.folkadev.folka_tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.folkadev.folka_tasks.domain.dto.TaskDto;
import com.folkadev.folka_tasks.domain.entities.Task;
import com.folkadev.folka_tasks.mappers.TaskMapper;

@Component
public class TaskMapperImpl implements TaskMapper {

  @Override
  public Task fromDto(TaskDto taskDto) {
    return new Task(
        taskDto.title(),
        taskDto.description(),
        taskDto.dueDate(),
        taskDto.priority(),
        taskDto.status(),
        null,
        null,
        null);
  }

  @Override
  public TaskDto toDto(Task task) {
    return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(),
        task.getStatus());
  }
}
