package com.hguitar.learning.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ReorderLessonsRequest(

    @NotEmpty(message = "items must not be empty")
    @Valid
    List<LessonOrderItem> items
) {
    public record LessonOrderItem(
        @NotNull UUID lessonId,
        @Min(1) int sortOrder
    ) {}
}
