package com.folkadev.folka_tasks.domain.dto;

import java.util.List;
import java.util.UUID;

public record TaskListDto(

    UUID id,
    String title,
    String description,
    Integer taskCount,
    Double progress,
    List<TaskDto> tasks) {

}
