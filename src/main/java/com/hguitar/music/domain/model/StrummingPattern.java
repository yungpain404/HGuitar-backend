package com.hguitar.music.domain.model;

import com.hguitar.common.domain.PublicationStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * A strumming pattern (cách đánh) presented as a beginner-friendly "formula".
 * formula uses symbols: ↓ = down, ↑ = up, - = pause/no strum.
 */
public class StrummingPattern {

    private final UUID id;
    private final String name;        // e.g. "Công thức 1: ↓ ↓↑↑ ↓ ↑"
    private final String formula;     // e.g. "↓ ↓↑↑ ↓ ↑"
    private final String description;
    private final String difficulty;  // BEGINNER | INTERMEDIATE | ADVANCED
    private final String guitarType;  // ACOUSTIC | CLASSICAL | ALL
    private final Integer bpmMin;
    private final Integer bpmMax;
    private final boolean isFormula;  // true = công thức dành cho người mới
    private final PublicationStatus status;
    private final int sortOrder;
    private final Instant createdAt;

    public StrummingPattern(UUID id, String name, String formula, String description,
                            String difficulty, String guitarType, Integer bpmMin, Integer bpmMax,
                            boolean isFormula, PublicationStatus status, int sortOrder, Instant createdAt) {
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
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getFormula() { return formula; }
    public String getDescription() { return description; }
    public String getDifficulty() { return difficulty; }
    public String getGuitarType() { return guitarType; }
    public Integer getBpmMin() { return bpmMin; }
    public Integer getBpmMax() { return bpmMax; }
    public boolean isFormula() { return isFormula; }
    public PublicationStatus getStatus() { return status; }
    public int getSortOrder() { return sortOrder; }
    public Instant getCreatedAt() { return createdAt; }
}
