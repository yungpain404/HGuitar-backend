package com.hguitar.auth.domain.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String email;
    private String passwordHash;
    private boolean active;
    private final Set<Role> roles;
    private final Instant createdAt;
    private Instant updatedAt;

    public User(UUID id, String email, String passwordHash,
                boolean active, Set<Role> roles,
                Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isActive() { return active; }
    public Set<Role> getRoles() { return roles; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
