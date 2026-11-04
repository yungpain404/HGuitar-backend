package com.hguitar.learning.application.dto.response;

import java.util.UUID;

public record LessonTranslationResponse(
    UUID id,
    String language,
    String title,
    String description
) {}
