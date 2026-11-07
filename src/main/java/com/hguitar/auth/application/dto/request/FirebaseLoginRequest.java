package com.hguitar.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FirebaseLoginRequest(
        @NotBlank(message = "Firebase ID token is required")
        String idToken
) {}
