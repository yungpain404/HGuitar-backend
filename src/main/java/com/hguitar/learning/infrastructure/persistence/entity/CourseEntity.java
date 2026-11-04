package com.hguitar.learning.infrastructure.persistence.entity;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.onboarding.domain.model.GuitarType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses", schema = "learning")
public class CourseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "guitar_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private GuitarType guitarType;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PublicationStatus status;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CourseTranslationEntity> translations = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected CourseEntity() {}

    public CourseEntity(UUID id, GuitarType guitarType, int sortOrder, PublicationStatus status) {
        this.id = id;
        this.guitarType = guitarType;
        this.sortOrder = sortOrder;
        this.status = status;
    }

    public UUID getId() { return id; }
    public GuitarType getGuitarType() { return guitarType; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public PublicationStatus getStatus() { return status; }
    public void setStatus(PublicationStatus status) { this.status = status; }
    public List<CourseTranslationEntity> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
