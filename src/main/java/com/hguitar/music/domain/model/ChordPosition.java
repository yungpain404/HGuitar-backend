package com.hguitar.music.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * One fingering position for a chord.
 * fingers: list of finger placements, each as {string, fret, finger} where
 *   string = 1..6 (1 = thinnest high e), fret = 0 means open, fret = -1 means muted
 */
public class ChordPosition {

    private final UUID id;
    private final UUID chordId;
    private final int positionNumber;
    private final int baseFret;           // starting fret for the diagram
    private final String fingersJson;     // JSON array of finger placements
    private final Instant createdAt;

    public ChordPosition(UUID id, UUID chordId, int positionNumber, int baseFret,
                         String fingersJson, Instant createdAt) {
        this.id = id;
        this.chordId = chordId;
        this.positionNumber = positionNumber;
        this.baseFret = baseFret;
        this.fingersJson = fingersJson;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getChordId() { return chordId; }
    public int getPositionNumber() { return positionNumber; }
    public int getBaseFret() { return baseFret; }
    public String getFingersJson() { return fingersJson; }
    public Instant getCreatedAt() { return createdAt; }
}
