package com.hguitar.music.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chord_positions", schema = "music")
public class ChordPositionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chord_id", nullable = false)
    private ChordEntity chord;

    @Column(name = "position_number", nullable = false)
    private int positionNumber;

    @Column(name = "base_fret", nullable = false)
    private int baseFret;

    @Column(name = "fingers_json", nullable = false, length = 2000)
    private String fingersJson;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected ChordPositionEntity() {}

    public ChordPositionEntity(UUID id, ChordEntity chord, int positionNumber,
                               int baseFret, String fingersJson) {
        this.id = id;
        this.chord = chord;
        this.positionNumber = positionNumber;
        this.baseFret = baseFret;
        this.fingersJson = fingersJson;
    }

    public UUID getId() { return id; }
    public ChordEntity getChord() { return chord; }
    public int getPositionNumber() { return positionNumber; }
    public int getBaseFret() { return baseFret; }
    public String getFingersJson() { return fingersJson; }
    public Instant getCreatedAt() { return createdAt; }
}
