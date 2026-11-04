package com.hguitar.onboarding.domain.model;

import java.time.Instant;
import java.util.UUID;

public class UserPreference {

    private final UUID id;
    private final UUID userId;
    private GuitarType guitarType;
    private boolean onboardingCompleted;
    private final Instant createdAt;
    private Instant updatedAt;

    public UserPreference(UUID id, UUID userId, GuitarType guitarType,
                          boolean onboardingCompleted, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.guitarType = guitarType;
        this.onboardingCompleted = onboardingCompleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void completeOnboarding(GuitarType guitarType) {
        this.guitarType = guitarType;
        this.onboardingCompleted = true;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public GuitarType getGuitarType() { return guitarType; }
    public boolean isOnboardingCompleted() { return onboardingCompleted; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
