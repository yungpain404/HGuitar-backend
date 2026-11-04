package com.hguitar.common.domain;

/**
 * Publication lifecycle shared across learning resources.
 * DRAFT    — not visible to students.
 * PUBLISHED — visible to students.
 * ARCHIVED — soft-deleted / hidden; cannot be reverted to DRAFT without explicit action.
 */
public enum PublicationStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED
}
