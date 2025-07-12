package com.folkadev.folka_tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.folkadev.folka_tasks.domain.entities.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
}
