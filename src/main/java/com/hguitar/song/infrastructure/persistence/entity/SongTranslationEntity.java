package com.hguitar.song.infrastructure.persistence.entity;

import com.hguitar.common.domain.Language;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "song_translations", schema = "song")
public class SongTranslationEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

    @Column(name = "language", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "artist", nullable = false, length = 500)
    private String artist;

    @Column(name = "description", length = 2000)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected SongTranslationEntity() {}

    public SongTranslationEntity(UUID id, SongEntity song, Language language, String title,
                                 String artist, String description) {
        this.id = id;
        this.song = song;
        this.language = language;
        this.title = title;
        this.artist = artist;
        this.description = description;
    }

    public UUID getId() { return id; }
    public SongEntity getSong() { return song; }
    public Language getLanguage() { return language; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getCreatedAt() { return createdAt; }
}
