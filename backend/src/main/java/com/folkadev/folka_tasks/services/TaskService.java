package com.folkadev.folka_tasks.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.folkadev.folka_tasks.domain.dto.TaskDto;

public interface TaskService {
  List<TaskDto> listTasks();

  TaskDto createTask(TaskDto taskDto, UUID listId);

  Optional<TaskDto> getTask(UUID taskId);

  TaskDto updateTask(UUID taskId, TaskDto taskDto);

  void deleteTask(UUID taskId);
}
