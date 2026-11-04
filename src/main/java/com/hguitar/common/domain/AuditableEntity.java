package com.hguitar.common.domain;

import java.time.Instant;

/**
 * Base audit fields carried by every persisted entity.
 * Domain layer — no JPA annotations here; JPA entity classes extend JpaAuditableEntity.
 */
public interface AuditableEntity {
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
