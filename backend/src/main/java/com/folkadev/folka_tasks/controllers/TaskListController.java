package com.folkadev.folka_tasks.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.services.TaskListService;

@Controller
@RequestMapping(path = "/task-lists")
public class TaskListController {

  private final TaskListService taskListService;

  public TaskListController(TaskListService taskListService) {
    this.taskListService = taskListService;
  }

  @GetMapping
  public List<TaskListDto> listTaskLists() {
    return taskListService.listTaskList();
  }

  @PostMapping
  public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
    return taskListService.createdTaskList(taskListDto);
  }

  @GetMapping(path = "/{task_list_id}")
  public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListid) {
    return taskListService.getTaskList(taskListid);
  }
}
