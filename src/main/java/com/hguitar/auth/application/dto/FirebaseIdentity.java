package com.hguitar.auth.application.dto;

/**
 * Identity extracted from a verified Firebase ID token.
 */
public record FirebaseIdentity(
        String subject,
        String email,
        String displayName,
        String avatarUrl
) {}
