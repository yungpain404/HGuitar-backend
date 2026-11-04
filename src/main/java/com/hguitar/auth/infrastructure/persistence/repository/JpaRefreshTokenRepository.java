package com.hguitar.auth.infrastructure.persistence.repository;

import com.hguitar.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByTokenHashAndRevokedFalseAndExpiresAtAfter(
            String tokenHash, Instant now);

    @Modifying
    @Query("UPDATE RefreshTokenEntity t SET t.revoked = true WHERE t.tokenHash = :hash")
    void revokeByTokenHash(@Param("hash") String hash);

    @Modifying
    @Query("UPDATE RefreshTokenEntity t SET t.revoked = true WHERE t.userId = :userId")
    void revokeAllByUserId(@Param("userId") UUID userId);
}
