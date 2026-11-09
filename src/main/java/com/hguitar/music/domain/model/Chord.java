package com.hguitar.music.domain.model;

import com.hguitar.onboarding.domain.model.GuitarType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Chord {

    private final UUID id;
    private final String name;    // e.g. "Am", "G", "C"
    private final GuitarType guitarType;
    private final ChordCategory category;
    private final String description;
    private final List<ChordPosition> positions;
    private final Instant createdAt;

    public Chord(UUID id, String name, GuitarType guitarType, ChordCategory category,
                 String description, List<ChordPosition> positions, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.guitarType = guitarType;
        this.category = category;
        this.description = description;
        this.positions = positions;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public GuitarType getGuitarType() { return guitarType; }
    public ChordCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public List<ChordPosition> getPositions() { return positions; }
    public Instant getCreatedAt() { return createdAt; }
}
