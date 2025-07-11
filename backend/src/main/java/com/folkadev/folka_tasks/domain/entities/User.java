package com.folkadev.folka_tasks.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "username")
  private String username;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "hashed_passowrd")
  private String hashedPassword;

  @OneToMany(mappedBy = "user")
  private List<Task> assignedTasks;

  @Column(name = "created")
  private LocalDateTime created;

  @Column(name = "updated")
  private LocalDateTime updated;

  public User() {
    // Default constructor
  }

  public User(String name, String username, String email, String hashedPassword, LocalDateTime created,
      LocalDateTime updated) {
    this.name = name;
    this.username = username;
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.created = created;
    this.updated = updated;
  }

  // Getters and Setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public List<Task> getAssignedTasks() {
    return assignedTasks;
  }

  public void setAssignedTasks(List<Task> assignedTasks) {
    this.assignedTasks = assignedTasks;
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

  // Create equals and hashCode methods
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return id.equals(user.id) &&
        name.equals(user.name) &&
        username.equals(user.username) &&
        email.equals(user.email) &&
        hashedPassword.equals(user.hashedPassword) &&
        created.equals(user.created) &&
        updated.equals(user.updated);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, name, username, email, hashedPassword, created, updated);
  }

  // Create toString method
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + "'" +
        ", username='" + username + "'" +
        ", email='" + email + "'" +
        ", hashedPassword='" + hashedPassword + "'" +
        ", assignedTasks=" + assignedTasks +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
