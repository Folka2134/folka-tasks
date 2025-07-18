package com.folkadev.folka_tasks.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.folkadev.folka_tasks.domain.dto.TaskDto;
import com.folkadev.folka_tasks.services.TaskService;

@RestController
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
    TaskDto createdTask = taskService.createTask(taskDto);
    return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
  }

  @GetMapping(path = "/{task_id}")
  public Optional<TaskDto> getTask(@PathVariable("task_id") UUID taskId) {
    return taskService.getTask(taskId);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{task_id}")
  public Optional<TaskDto> updateTask(@PathVariable("task_id") UUID taskId, TaskDto taskDto) {
    return taskService.updateTask(taskId, taskDto);
  }

  @DeleteMapping(path = "/{task_id")
  public void deleteTask(@PathVariable("task_id") UUID taskId) {
    taskService.deleteTask(taskId);
  }

}
