package com.folkadev.folka_tasks.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "dueDate")
  private LocalDateTime dueDate;

  @Column(name = "priority")
  private TaskPriority priority;

  @Column(name = "status", nullable = false)
  private TaskStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_list_id")
  private TaskList taskList;

  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Task() {
  }

  public Task(String title, String description, LocalDateTime dueDate, TaskPriority priority,
      TaskStatus status, TaskList task, LocalDateTime created, LocalDateTime updated) {
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.taskList = task;
    this.created = created;
    this.updated = updated;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public void setPriority(TaskPriority priority) {
    this.priority = priority;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public TaskList getTaskList() {
    return taskList;
  }

  public void setTaskList(TaskList taskList) {
    this.taskList = taskList;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Task task = (Task) o;
    return Objects.equals(id, task.id) &&
        Objects.equals(title, task.title) &&
        Objects.equals(description, task.description) &&
        Objects.equals(dueDate, task.dueDate) &&
        priority == task.priority &&
        status == task.status &&
        Objects.equals(taskList, task.taskList) &&
        Objects.equals(created, task.created) &&
        Objects.equals(updated, task.updated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, dueDate, priority, status, taskList, created, updated);
  }

  @Override
  public String toString() {
    return "Task{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", dueDate=" + dueDate +
        ", priority=" + priority +
        ", status=" + status +
        ", taskList=" + taskList +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
