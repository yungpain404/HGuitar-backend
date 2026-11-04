package com.hguitar.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank(message = "Display name is required")
        @Size(max = 255, message = "Display name must be at most 255 characters")
        String displayName,

        @Size(max = 500, message = "Avatar URL must be at most 500 characters")
        String avatarUrl
) {}
