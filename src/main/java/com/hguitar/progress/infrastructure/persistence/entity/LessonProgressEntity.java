package com.hguitar.progress.infrastructure.persistence.entity;

import com.hguitar.progress.domain.model.LessonProgress;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_lesson_progress", schema = "progress",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_lesson_progress_user_lesson",
                columnNames = {"user_id", "lesson_id"}))
public class LessonProgressEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "lesson_id", nullable = false)
    private UUID lessonId;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LessonProgress.Status status;

    @Column(name = "completed_at")
    private Instant completedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected LessonProgressEntity() {}

    public LessonProgressEntity(UUID id, UUID userId, UUID lessonId, LessonProgress.Status status) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getLessonId() { return lessonId; }
    public LessonProgress.Status getStatus() { return status; }
    public void setStatus(LessonProgress.Status status) { this.status = status; }
    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
