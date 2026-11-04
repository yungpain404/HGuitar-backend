package com.hguitar.auth.domain.model;

/**
 * Application roles. Backend enforces these; frontend must not rely on role checks for security.
 */
public enum Role {
    STUDENT,
    CONTENT_EDITOR,
    ADMIN
}
