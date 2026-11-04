package com.hguitar.learning.application.dto.response;

import com.hguitar.common.domain.PublicationStatus;

import java.util.UUID;

public record LessonResponse(
        UUID id,
        UUID moduleId,
        int sortOrder,
        int durationMinutes,
        PublicationStatus status,
        String title,
        String description
) {}
