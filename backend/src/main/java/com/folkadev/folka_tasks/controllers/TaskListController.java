package com.folkadev.folka_tasks.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.services.TaskListService;

@RestController
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
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto taskListDto) {
    TaskListDto createdTaskList = taskListService.createdTaskList(taskListDto);
    return new ResponseEntity<>(createdTaskList, HttpStatus.CREATED);
  }

  @GetMapping(path = "/{task_list_id}")
  public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListid) {
    return taskListService.getTaskList(taskListid);
  }

  @DeleteMapping(path = "/{task_list_id}")
  public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
    taskListService.deleteTaskList(taskListId);
  }

  // @PutMapping(path = "/{task_list_id}")
  @RequestMapping(method = RequestMethod.PUT, path = "/{task_list_id}")
  public Optional<TaskListDto> updateTaskList(@PathVariable("task_list_id") UUID taskListId,
      @RequestBody TaskListDto taskListDto) {
    return taskListService.updateTaskList(taskListId, taskListDto);
  }
}
