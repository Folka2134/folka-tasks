package com.folkadev.folka_tasks.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.folkadev.folka_tasks.domain.dto.TaskDto;
import com.folkadev.folka_tasks.domain.entities.TaskList;
import com.folkadev.folka_tasks.domain.entities.TaskPriority;
import com.folkadev.folka_tasks.domain.entities.TaskStatus;
import com.folkadev.folka_tasks.mappers.TaskMapper;
import com.folkadev.folka_tasks.repositories.TaskListRepository;
import com.folkadev.folka_tasks.repositories.TaskRepository;
import com.folkadev.folka_tasks.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;
  private final TaskListRepository taskListRepository;

  public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, TaskListRepository taskListRepository) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
    this.taskListRepository = taskListRepository;
  }

  @Override
  public List<TaskDto> listTasks() {
    return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
  }

  @Override
  public TaskDto createTask(TaskDto taskDto, UUID listId) {
    if (taskDto.id() != null) {
      throw new IllegalArgumentException("Task already has an id");
    }

    if (taskDto.title() == null | taskDto.title().isBlank()) {
      throw new IllegalArgumentException("Task is missing a title");
    }

    if (listId == null) {
      throw new IllegalArgumentException("Task list id is required");
    }

    TaskList taskList = taskListRepository.findById(listId).orElseThrow(
        () -> new IllegalArgumentException("Task list with id " + listId + " does not exist"));

    var task = taskMapper.fromDto(taskDto);
    if (task.getStatus() == null) {
      task.setStatus(TaskStatus.OPEN);
    }
    if (task.getPriority() == null) {
      task.setPriority(TaskPriority.LOW);
    }
    task.setTaskList(taskList);

    var savedTask = taskRepository.save(task);
    return taskMapper.toDto(savedTask);
  }

  @Override
  public Optional<TaskDto> getTask(UUID taskId) {
    return taskRepository.findById(taskId).map(taskMapper::toDto);
  }

  @Override
  public TaskDto updateTask(UUID taskId, TaskDto taskDto) {
    // TODO Auto-generated method stub
    return taskDto;
  }

  @Override
  public void deleteTask(UUID taskId) {
    // TODO Auto-generated method stub

  }
}
