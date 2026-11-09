package com.hguitar.learning.infrastructure.persistence.entity;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learning_methods", schema = "learning")
public class LearningMethodEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name_key", nullable = false, length = 100)
    private String nameKey;

    @Column(name = "difficulty", nullable = false, length = 20)
    private String difficulty;

    @Column(name = "target_guitar", nullable = false, length = 20)
    private String targetGuitar;

    @Column(name = "estimated_weeks", nullable = false)
    private int estimatedWeeks;

    @Column(name = "steps_count", nullable = false)
    private int stepsCount;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PublicationStatus status;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "method", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<LearningMethodTranslationEntity> translations = new ArrayList<>();

    protected LearningMethodEntity() {}

    public LearningMethodEntity(UUID id, String nameKey, String difficulty, String targetGuitar,
                                int estimatedWeeks, int stepsCount, PublicationStatus status, int sortOrder) {
        this.id = id;
        this.nameKey = nameKey;
        this.difficulty = difficulty;
        this.targetGuitar = targetGuitar;
        this.estimatedWeeks = estimatedWeeks;
        this.stepsCount = stepsCount;
        this.status = status;
        this.sortOrder = sortOrder;
    }

    public UUID getId() { return id; }
    public String getNameKey() { return nameKey; }
    public void setNameKey(String nameKey) { this.nameKey = nameKey; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getTargetGuitar() { return targetGuitar; }
    public void setTargetGuitar(String targetGuitar) { this.targetGuitar = targetGuitar; }
    public int getEstimatedWeeks() { return estimatedWeeks; }
    public void setEstimatedWeeks(int estimatedWeeks) { this.estimatedWeeks = estimatedWeeks; }
    public int getStepsCount() { return stepsCount; }
    public void setStepsCount(int stepsCount) { this.stepsCount = stepsCount; }
    public PublicationStatus getStatus() { return status; }
    public void setStatus(PublicationStatus status) { this.status = status; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<LearningMethodTranslationEntity> getTranslations() { return translations; }
}
