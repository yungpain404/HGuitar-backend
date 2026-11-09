package com.hguitar.song.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "song_chords", schema = "song")
public class SongChordEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

    @Column(name = "chord_id", nullable = false)
    private UUID chordId;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected SongChordEntity() {}

    public SongChordEntity(UUID id, SongEntity song, UUID chordId, int sortOrder, boolean isRequired) {
        this.id = id;
        this.song = song;
        this.chordId = chordId;
        this.sortOrder = sortOrder;
        this.isRequired = isRequired;
    }

    public UUID getId() { return id; }
    public SongEntity getSong() { return song; }
    public UUID getChordId() { return chordId; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { this.isRequired = required; }
    public Instant getCreatedAt() { return createdAt; }
}
