package com.hguitar.learning.infrastructure.persistence.entity;

import com.hguitar.common.domain.Language;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learning_method_translations", schema = "learning")
public class LearningMethodTranslationEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", nullable = false)
    private LearningMethodEntity method;

    @Column(name = "language", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "steps_json", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String stepsJson;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected LearningMethodTranslationEntity() {}

    public LearningMethodTranslationEntity(UUID id, LearningMethodEntity method, Language language,
                                           String title, String description, String stepsJson) {
        this.id = id;
        this.method = method;
        this.language = language;
        this.title = title;
        this.description = description;
        this.stepsJson = stepsJson;
    }

    public UUID getId() { return id; }
    public LearningMethodEntity getMethod() { return method; }
    public Language getLanguage() { return language; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStepsJson() { return stepsJson; }
    public void setStepsJson(String stepsJson) { this.stepsJson = stepsJson; }
    public Instant getCreatedAt() { return createdAt; }
}
