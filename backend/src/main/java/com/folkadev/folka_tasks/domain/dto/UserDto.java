package com.folkadev.folka_tasks.domain.dto;

import java.util.UUID;

public record UserDto(
    UUID id,
    String name,
    String username,
    String email) {
}
