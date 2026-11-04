package com.hguitar.onboarding.infrastructure.persistence.repository;

import com.hguitar.onboarding.domain.model.UserPreference;
import com.hguitar.onboarding.domain.repository.UserPreferenceRepository;
import com.hguitar.onboarding.infrastructure.persistence.entity.UserPreferenceEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserPreferenceRepositoryAdapter implements UserPreferenceRepository {

    private final JpaUserPreferenceRepository jpa;

    public UserPreferenceRepositoryAdapter(JpaUserPreferenceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserPreference save(UserPreference preference) {
        UserPreferenceEntity existing = jpa.findByUserId(preference.getUserId()).orElse(null);
        UserPreferenceEntity entity;
        if (existing != null) {
            existing.setGuitarType(preference.getGuitarType());
            existing.setOnboardingCompleted(preference.isOnboardingCompleted());
            entity = jpa.save(existing);
        } else {
            entity = jpa.save(new UserPreferenceEntity(
                    preference.getId(), preference.getUserId(),
                    preference.getGuitarType(), preference.isOnboardingCompleted()));
        }
        return toDomain(entity);
    }

    @Override
    public Optional<UserPreference> findByUserId(UUID userId) {
        return jpa.findByUserId(userId).map(this::toDomain);
    }

    private UserPreference toDomain(UserPreferenceEntity e) {
        return new UserPreference(e.getId(), e.getUserId(), e.getGuitarType(),
                e.isOnboardingCompleted(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
