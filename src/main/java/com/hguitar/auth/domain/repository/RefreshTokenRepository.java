package com.hguitar.auth.domain.repository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    void save(UUID userId, String tokenHash, java.time.Instant expiresAt);
    Optional<UUID> findUserIdByTokenHash(String tokenHash);
    void revokeByTokenHash(String tokenHash);
    void revokeAllByUserId(UUID userId);
}
