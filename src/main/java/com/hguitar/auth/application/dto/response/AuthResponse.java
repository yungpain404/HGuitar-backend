package com.hguitar.auth.application.dto.response;

import java.util.List;
import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String email,
        String displayName,
        List<String> roles,
        String accessToken,
        String refreshToken
) {}
