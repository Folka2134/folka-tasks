package com.folkadev.folka_tasks.mappers;

import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.domain.entities.TaskList;

public interface TaskListMapper {

  TaskList fromDto(TaskListDto taskListDto);

  TaskListDto toDto(TaskList taskList);

}
