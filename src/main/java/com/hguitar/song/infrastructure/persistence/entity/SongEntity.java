package com.hguitar.song.infrastructure.persistence.entity;

import com.hguitar.common.domain.PublicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "songs", schema = "song")
public class SongEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "guitar_type", nullable = false, length = 20)
    private String guitarType;

    @Column(name = "difficulty", nullable = false, length = 20)
    private String difficulty;

    @Column(name = "source", nullable = false, length = 20)
    private String source;

    @Column(name = "original_key", length = 10)
    private String originalKey;

    @Column(name = "capo_fret", nullable = false)
    private int capoFret;

    @Column(name = "bpm")
    private Integer bpm;

    @Column(name = "strumming_pattern_id")
    private UUID strummingPatternId;

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

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<SongTranslationEntity> translations = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<SongChordEntity> chords = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("sectionOrder ASC")
    private List<SongSectionEntity> sections = new ArrayList<>();

    protected SongEntity() {}

    public SongEntity(UUID id, String guitarType, String difficulty, String source, String originalKey,
                      int capoFret, Integer bpm, UUID strummingPatternId, boolean isFormula,
                      PublicationStatus status, int sortOrder) {
        this.id = id;
        this.guitarType = guitarType;
        this.difficulty = difficulty;
        this.source = source;
        this.originalKey = originalKey;
        this.capoFret = capoFret;
        this.bpm = bpm;
        this.strummingPatternId = strummingPatternId;
        this.isFormula = isFormula;
        this.status = status;
        this.sortOrder = sortOrder;
    }

    public UUID getId() { return id; }
    public String getGuitarType() { return guitarType; }
    public void setGuitarType(String guitarType) { this.guitarType = guitarType; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getOriginalKey() { return originalKey; }
    public void setOriginalKey(String originalKey) { this.originalKey = originalKey; }
    public int getCapoFret() { return capoFret; }
    public void setCapoFret(int capoFret) { this.capoFret = capoFret; }
    public Integer getBpm() { return bpm; }
    public void setBpm(Integer bpm) { this.bpm = bpm; }
    public UUID getStrummingPatternId() { return strummingPatternId; }
    public void setStrummingPatternId(UUID strummingPatternId) { this.strummingPatternId = strummingPatternId; }
    public boolean isFormula() { return isFormula; }
    public void setFormula(boolean formula) { this.isFormula = formula; }
    public PublicationStatus getStatus() { return status; }
    public void setStatus(PublicationStatus status) { this.status = status; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<SongTranslationEntity> getTranslations() { return translations; }
    public List<SongChordEntity> getChords() { return chords; }
    public List<SongSectionEntity> getSections() { return sections; }
}
