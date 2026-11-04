package com.hguitar.onboarding.application.dto.request;

import com.hguitar.onboarding.domain.model.GuitarType;
import jakarta.validation.constraints.NotNull;

public record CompleteOnboardingRequest(
        @NotNull(message = "Guitar type is required")
        GuitarType guitarType
) {}
