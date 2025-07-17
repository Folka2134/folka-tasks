package com.folkadev.folka_tasks.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
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
  public List<TaskListDto> listTaskList() {
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
  public Optional<TaskListDto> updateTaskList(UUID taskListId, TaskListDto taskListDto) {
    if (!taskListRepository.existsById(taskListId)) {
      throw new IllegalArgumentException("Task list with id " + taskListId + " does not exist");
    }

    var taskListToUpdate = taskListRepository.findById(taskListId);

    taskListToUpdate.ifPresent(taskList -> {
      if (taskListDto.title() != null && !taskListDto.title().isBlank()) {
        taskList.setTitle(taskListDto.title());
      }
      if (taskListDto.description() != null) {
        taskList.setDescription(taskListDto.description());
      }
    });

    taskListToUpdate.ifPresent(taskListRepository::save);
    return taskListToUpdate.map(taskListMapper::toDto);
  }

}
