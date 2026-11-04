package com.hguitar.user.domain.model;

import java.time.Instant;
import java.util.UUID;

public class UserProfile {

    private final UUID id;
    private final UUID userId;
    private String displayName;
    private String avatarUrl;
    private final Instant createdAt;
    private Instant updatedAt;

    public UserProfile(UUID id, UUID userId, String displayName,
                       String avatarUrl, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String displayName, String avatarUrl) {
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
