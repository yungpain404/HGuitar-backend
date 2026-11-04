package com.hguitar.learning.application.dto.response;

import com.hguitar.common.domain.PublicationStatus;

import java.util.UUID;

public record ModuleResponse(
        UUID id,
        UUID courseId,
        int sortOrder,
        PublicationStatus status,
        String title,
        String description
) {}
