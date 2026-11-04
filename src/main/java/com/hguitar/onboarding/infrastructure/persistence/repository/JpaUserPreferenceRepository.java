package com.hguitar.onboarding.infrastructure.persistence.repository;

import com.hguitar.onboarding.infrastructure.persistence.entity.UserPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserPreferenceRepository extends JpaRepository<UserPreferenceEntity, UUID> {
    Optional<UserPreferenceEntity> findByUserId(UUID userId);
}
