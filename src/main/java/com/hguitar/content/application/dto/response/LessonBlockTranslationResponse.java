package com.hguitar.content.application.dto.response;

import java.util.UUID;

public record LessonBlockTranslationResponse(
    UUID id,
    String language,
    String content
) {}
