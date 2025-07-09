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

  @Column(name = "status")
  private TaskPriority priority;

  @Column(name = "status", nullable = false)
  private TaskStatus status;

  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  // @Column(name = "taskList")
  // private TaskList taskList;
  //

  public Task() {

  }

  public Task(UUID id, String title, String description, LocalDateTime dueDate, TaskPriority priority,
      TaskStatus status, LocalDateTime created, LocalDateTime updated) {

    this.id = id;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.created = created;
    this.updated = updated;
  }

  // Generate getters and setters
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

  // Generate equals and hashCode methods
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
        Objects.equals(created, task.created) &&
        Objects.equals(updated, task.updated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, dueDate, priority, status, created, updated);
  }

  // Generate toString method
  @Override
  public String toString() {
    return "Task{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", dueDate=" + dueDate +
        ", priority=" + priority +
        ", status=" + status +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }

}
