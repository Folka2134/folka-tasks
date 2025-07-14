package com.folkadev.folka_tasks.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.folkadev.folka_tasks.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findById(UUID userId);
}
