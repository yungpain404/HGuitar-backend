package com.hguitar.user.infrastructure.persistence.repository;

import com.hguitar.user.domain.model.UserProfile;
import com.hguitar.user.domain.repository.UserProfileRepository;
import com.hguitar.user.infrastructure.persistence.entity.UserProfileEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserProfileRepositoryAdapter implements UserProfileRepository {

    private final JpaUserProfileRepository jpa;

    public UserProfileRepositoryAdapter(JpaUserProfileRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserProfile save(UserProfile profile) {
        UserProfileEntity existing = jpa.findByUserId(profile.getUserId()).orElse(null);
        UserProfileEntity entity;
        if (existing != null) {
            existing.setDisplayName(profile.getDisplayName());
            existing.setAvatarUrl(profile.getAvatarUrl());
            entity = jpa.save(existing);
        } else {
            entity = jpa.save(new UserProfileEntity(
                    profile.getId(), profile.getUserId(),
                    profile.getDisplayName(), profile.getAvatarUrl()));
        }
        return toDomain(entity);
    }

    @Override
    public Optional<UserProfile> findByUserId(UUID userId) {
        return jpa.findByUserId(userId).map(this::toDomain);
    }

    private UserProfile toDomain(UserProfileEntity e) {
        return new UserProfile(e.getId(), e.getUserId(), e.getDisplayName(),
                e.getAvatarUrl(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
