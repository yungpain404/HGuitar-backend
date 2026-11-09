package com.hguitar.music.application.dto.response;

import java.time.Instant;
import java.util.UUID;

public record StrummingPatternResponse(
        UUID id,
        String name,
        String formula,
        String description,
        String difficulty,
        String guitarType,
        Integer bpmMin,
        Integer bpmMax,
        boolean isFormula,
        String status,
        int sortOrder,
        Instant createdAt
) {}
