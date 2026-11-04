package com.hguitar.user.application.dto.response;

import java.util.UUID;

public record UserProfileResponse(
        UUID userId,
        String email,
        String displayName,
        String avatarUrl
) {}
