package com.folkadev.folka_tasks.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.folkadev.folka_tasks.domain.dto.TaskDto;
import com.folkadev.folka_tasks.domain.dto.TaskListDto;

public interface TaskListService {
  List<TaskListDto> listTaskLists();

  List<TaskDto> getTasksFromTaskList(UUID taskListId);

  TaskListDto createdTaskList(TaskListDto taskListDto);

  Optional<TaskListDto> getTaskList(UUID taskListId);

  void deleteTaskList(UUID taskListId);

  TaskListDto updateTaskList(UUID taskLIdID, TaskListDto taskListDto);

}
