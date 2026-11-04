package com.hguitar.learning.application.dto.response;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.onboarding.domain.model.GuitarType;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        GuitarType guitarType,
        int sortOrder,
        PublicationStatus status,
        String title,
        String description
) {}
