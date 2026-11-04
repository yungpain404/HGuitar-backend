package com.hguitar.learning.application.dto.response;

import com.hguitar.common.domain.PublicationStatus;

import java.util.UUID;

/**
 * Lightweight lesson representation used in module/lesson list views.
 */
public record LessonSummaryResponse(
    UUID id,
    UUID moduleId,
    int sortOrder,
    int durationMinutes,
    PublicationStatus status,
    String title,
    String description
) {}
