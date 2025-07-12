package com.folkadev.folka_tasks.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.folkadev.folka_tasks.domain.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
  // Look for all tasks related to TaskList
  List<Task> findByTaskListId(UUID taskListId);

  // Look for single task in TaskList
  Optional<Task> findByTaskListIdandId(UUID taskListId, UUID id);
}
