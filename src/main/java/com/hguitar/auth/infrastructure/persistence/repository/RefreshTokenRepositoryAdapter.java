package com.hguitar.auth.infrastructure.persistence.repository;

import com.hguitar.auth.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpa;

    public RefreshTokenRepositoryAdapter(JpaRefreshTokenRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(UUID userId, String tokenHash, Instant expiresAt) {
        var entity = new com.hguitar.auth.infrastructure.persistence.entity.RefreshTokenEntity(
                UUID.randomUUID(), userId, tokenHash, expiresAt);
        jpa.save(entity);
    }

    @Override
    public Optional<UUID> findUserIdByTokenHash(String tokenHash) {
        return jpa.findByTokenHashAndRevokedFalseAndExpiresAtAfter(tokenHash, Instant.now())
                .map(e -> e.getUserId());
    }

    @Override
    @Transactional
    public void revokeByTokenHash(String tokenHash) {
        jpa.revokeByTokenHash(tokenHash);
    }

    @Override
    @Transactional
    public void revokeAllByUserId(UUID userId) {
        jpa.revokeAllByUserId(userId);
    }
}
