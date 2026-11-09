package com.hguitar.song.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "song_sections", schema = "song")
public class SongSectionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

    @Column(name = "section_type", nullable = false, length = 20)
    private String sectionType;

    @Column(name = "section_order", nullable = false)
    private int sectionOrder;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "chord_progression", nullable = false, length = 1000)
    private String chordProgression;

    @Column(name = "strumming_pattern_id")
    private UUID strummingPatternId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected SongSectionEntity() {}

    public SongSectionEntity(UUID id, SongEntity song, String sectionType, int sectionOrder,
                             String name, String chordProgression, UUID strummingPatternId) {
        this.id = id;
        this.song = song;
        this.sectionType = sectionType;
        this.sectionOrder = sectionOrder;
        this.name = name;
        this.chordProgression = chordProgression;
        this.strummingPatternId = strummingPatternId;
    }

    public UUID getId() { return id; }
    public SongEntity getSong() { return song; }
    public String getSectionType() { return sectionType; }
    public int getSectionOrder() { return sectionOrder; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getChordProgression() { return chordProgression; }
    public void setChordProgression(String chordProgression) { this.chordProgression = chordProgression; }
    public UUID getStrummingPatternId() { return strummingPatternId; }
    public void setStrummingPatternId(UUID strummingPatternId) { this.strummingPatternId = strummingPatternId; }
    public Instant getCreatedAt() { return createdAt; }
}
