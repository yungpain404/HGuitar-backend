package com.hguitar.user.domain.repository;

import com.hguitar.user.domain.model.UserProfile;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository {
    UserProfile save(UserProfile profile);
    Optional<UserProfile> findByUserId(UUID userId);
}
