package com.folkadev.folka_tasks.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.folkadev.folka_tasks.domain.entities.TaskList;

@Service
public interface TaskListService {
  List<TaskList> listTaskList();
}
