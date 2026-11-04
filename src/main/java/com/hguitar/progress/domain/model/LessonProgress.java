package com.hguitar.progress.domain.model;

import java.time.Instant;
import java.util.UUID;

public class LessonProgress {

    public enum Status { NOT_STARTED, IN_PROGRESS, COMPLETED }

    private final UUID id;
    private final UUID userId;
    private final UUID lessonId;
    private Status status;
    private Instant completedAt;
    private final Instant createdAt;
    private Instant updatedAt;

    public LessonProgress(UUID id, UUID userId, UUID lessonId, Status status,
                          Instant completedAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.status = status;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void complete() {
        if (this.status != Status.COMPLETED) {
            this.status = Status.COMPLETED;
            this.completedAt = Instant.now();
            this.updatedAt = Instant.now();
        }
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getLessonId() { return lessonId; }
    public Status getStatus() { return status; }
    public Instant getCompletedAt() { return completedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
