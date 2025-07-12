package com.folkadev.folka_tasks.services.impl;

import java.util.List;

import com.folkadev.folka_tasks.domain.entities.TaskList;
import com.folkadev.folka_tasks.repositories.TaskListRepository;
import com.folkadev.folka_tasks.services.TaskListService;

public class TaskListServiceImpl implements TaskListService {

  private final TaskListRepository taskListRepository;

  public TaskListServiceImpl(TaskListRepository taskListRepository) {
    this.taskListRepository = taskListRepository;
  }

  @Override
  public List<TaskList> listTaskList() {
    return taskListRepository.findAll();
  }
}
