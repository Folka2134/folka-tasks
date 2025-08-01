package com.folkadev.folka_tasks.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.domain.entities.TaskList;
import com.folkadev.folka_tasks.exceptions.ResourceNotFoundException;
import com.folkadev.folka_tasks.mappers.TaskListMapper;
import com.folkadev.folka_tasks.repositories.TaskListRepository;
import com.folkadev.folka_tasks.services.TaskListService;

@Service
public class TaskListServiceImpl implements TaskListService {

  private final TaskListRepository taskListRepository;
  private final TaskListMapper taskListMapper;

  public TaskListServiceImpl(TaskListRepository taskListRepository, TaskListMapper taskListMapper) {
    this.taskListRepository = taskListRepository;
    this.taskListMapper = taskListMapper;
  }

  @Override
  public List<TaskListDto> listTaskLists() {
    return taskListRepository.findAll().stream().map(taskListMapper::toDto).toList();
  }

  @Override
  public TaskListDto createdTaskList(TaskListDto taskListDto) {
    if (taskListDto.id() != null) {
      throw new IllegalArgumentException("Task list already has an id");
    }

    if (taskListDto.title() == null || taskListDto.title().isBlank()) {
      throw new IllegalArgumentException("Task list title must be present");
    }

    var taskList = taskListMapper.fromDto(taskListDto);
    var savedTaskList = taskListRepository.save(taskList);
    return taskListMapper.toDto(savedTaskList);
  }

  @Override
  public Optional<TaskListDto> getTaskList(UUID taskListId) {
    return taskListRepository.findById(taskListId).map(taskListMapper::toDto);
  }

  @Override
  public void deleteTaskList(UUID taskListId) {
    if (!taskListRepository.existsById(taskListId)) {
      throw new IllegalArgumentException("Task list with id " + taskListId + " does not exist");
    }
    taskListRepository.deleteById(taskListId);
  }

  @Override
  public TaskListDto updateTaskList(UUID taskListId, TaskListDto taskListDto) {
    if (!Objects.equals(taskListId, taskListDto.id())) {
      throw new IllegalArgumentException("Changing task list id is not permitted");
    }

    TaskList taskListToUpdate = taskListRepository.findById(taskListId).orElseThrow(() -> {
      throw new ResourceNotFoundException("Task list with id " + taskListId + " does not exist");
    });

    if (taskListDto.title() != null && !taskListDto.title().isBlank()) {
      taskListToUpdate.setTitle(taskListDto.title());
    }
    if (taskListDto.description() != null) {
      taskListToUpdate.setDescription(taskListDto.description());
    }
    TaskList updatedTaskList = taskListRepository.save(taskListToUpdate);
    return taskListMapper.toDto(updatedTaskList);
  }

}
