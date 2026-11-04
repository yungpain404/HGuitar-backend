package com.hguitar.learning.infrastructure.persistence.entity;

import com.hguitar.common.domain.PublicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lessons", schema = "learning")
public class LessonEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "module_id", nullable = false)
    private UUID moduleId;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PublicationStatus status;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<LessonTranslationEntity> translations = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected LessonEntity() {}

    public LessonEntity(UUID id, UUID moduleId, int sortOrder, int durationMinutes, PublicationStatus status) {
        this.id = id;
        this.moduleId = moduleId;
        this.sortOrder = sortOrder;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getModuleId() { return moduleId; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public PublicationStatus getStatus() { return status; }
    public void setStatus(PublicationStatus status) { this.status = status; }
    public List<LessonTranslationEntity> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
