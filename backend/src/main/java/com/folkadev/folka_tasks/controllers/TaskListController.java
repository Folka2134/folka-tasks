package com.folkadev.folka_tasks.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.mappers.TaskListMapper;
import com.folkadev.folka_tasks.services.TaskListService;

@Controller
@RequestMapping(path = "/task-lists")
public class TaskListController {

  private final TaskListService taskListService;
  private final TaskListMapper taskListMapper;

  public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
    this.taskListService = taskListService;
    this.taskListMapper = taskListMapper;
  }

  @GetMapping
  public List<TaskListDto> listTaskLists() {
    return taskListService.listTaskList().stream().map(taskListMapper::toDto).toList();
  }

}
