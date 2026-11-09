package com.hguitar.music.infrastructure.persistence.entity;

import com.hguitar.common.domain.PublicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "strumming_patterns", schema = "music")
public class StrummingPatternEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "formula", nullable = false, length = 100)
    private String formula;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "difficulty", nullable = false, length = 20)
    private String difficulty;

    @Column(name = "guitar_type", nullable = false, length = 20)
    private String guitarType;

    @Column(name = "bpm_min")
    private Integer bpmMin;

    @Column(name = "bpm_max")
    private Integer bpmMax;

    @Column(name = "is_formula", nullable = false)
    private boolean isFormula;

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

    protected StrummingPatternEntity() {}

    public StrummingPatternEntity(UUID id, String name, String formula, String description,
                                  String difficulty, String guitarType, Integer bpmMin, Integer bpmMax,
                                  boolean isFormula, PublicationStatus status, int sortOrder) {
        this.id = id;
        this.name = name;
        this.formula = formula;
        this.description = description;
        this.difficulty = difficulty;
        this.guitarType = guitarType;
        this.bpmMin = bpmMin;
        this.bpmMax = bpmMax;
        this.isFormula = isFormula;
        this.status = status;
        this.sortOrder = sortOrder;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getGuitarType() { return guitarType; }
    public void setGuitarType(String guitarType) { this.guitarType = guitarType; }
    public Integer getBpmMin() { return bpmMin; }
    public void setBpmMin(Integer bpmMin) { this.bpmMin = bpmMin; }
    public Integer getBpmMax() { return bpmMax; }
    public void setBpmMax(Integer bpmMax) { this.bpmMax = bpmMax; }
    public boolean isFormula() { return isFormula; }
    public void setFormula(boolean formula) { this.isFormula = formula; }
    public PublicationStatus getStatus() { return status; }
    public void setStatus(PublicationStatus status) { this.status = status; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
