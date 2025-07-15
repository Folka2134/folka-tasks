package com.folkadev.folka_tasks.domain.dto;

public record ErrorResponse(
    int statusCode,
    String errorType,
    String errorDetails) {

}
