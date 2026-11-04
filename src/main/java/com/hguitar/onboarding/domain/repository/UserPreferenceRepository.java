package com.hguitar.onboarding.domain.repository;

import com.hguitar.onboarding.domain.model.UserPreference;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository {
    UserPreference save(UserPreference preference);
    Optional<UserPreference> findByUserId(UUID userId);
}
