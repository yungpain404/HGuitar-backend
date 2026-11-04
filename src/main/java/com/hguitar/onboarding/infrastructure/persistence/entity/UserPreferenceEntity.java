package com.hguitar.onboarding.infrastructure.persistence.entity;

import com.hguitar.onboarding.domain.model.GuitarType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_preferences", schema = "auth")
public class UserPreferenceEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "guitar_type", length = 20)
    @Enumerated(EnumType.STRING)
    private GuitarType guitarType;

    @Column(name = "onboarding_completed", nullable = false)
    private boolean onboardingCompleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected UserPreferenceEntity() {}

    public UserPreferenceEntity(UUID id, UUID userId, GuitarType guitarType, boolean onboardingCompleted) {
        this.id = id;
        this.userId = userId;
        this.guitarType = guitarType;
        this.onboardingCompleted = onboardingCompleted;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public GuitarType getGuitarType() { return guitarType; }
    public void setGuitarType(GuitarType guitarType) { this.guitarType = guitarType; }
    public boolean isOnboardingCompleted() { return onboardingCompleted; }
    public void setOnboardingCompleted(boolean onboardingCompleted) { this.onboardingCompleted = onboardingCompleted; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
