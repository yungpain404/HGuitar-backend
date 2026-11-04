package com.hguitar.onboarding.application.dto.response;

import com.hguitar.onboarding.domain.model.GuitarType;

public record OnboardingStatusResponse(
        boolean completed,
        GuitarType guitarType
) {}
