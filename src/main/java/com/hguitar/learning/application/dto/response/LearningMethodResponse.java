package com.hguitar.learning.application.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LearningMethodResponse(
        UUID id,
        String nameKey,
        String difficulty,
        String targetGuitar,
        int estimatedWeeks,
        int stepsCount,
        String status,
        int sortOrder,
        Instant createdAt,
        String title,
        String description,
        List<String> steps
) {}
