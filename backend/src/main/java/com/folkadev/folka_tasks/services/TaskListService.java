package com.folkadev.folka_tasks.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;

public interface TaskListService {
  List<TaskListDto> listTaskList();

  TaskListDto createdTaskList(TaskListDto taskListDto);

  Optional<TaskListDto> getTaskList(UUID taskListId);

  void deleteTaskList(UUID taskListId);

  Optional<TaskListDto> updateTaskList(UUID taskLIdID, TaskListDto taskListDto);

}
