package com.hguitar.song.domain.model;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A song in the beginner repertoire (bài hát dễ đánh).
 * Holds translations and the ordered list of chords + sections.
 */
public class Song {

    private final UUID id;
    private final String guitarType;   // ACOUSTIC | CLASSICAL
    private final String difficulty;   // BEGINNER | INTERMEDIATE | ADVANCED
    private final String source;       // VIETNAMESE | FOREIGN
    private final String originalKey;  // e.g. "C", "G", "Am"
    private final int capoFret;
    private final Integer bpm;
    private final UUID strummingPatternId; // recommended strumming pattern
    private final boolean isFormula;   // uses beginner formula strumming
    private final PublicationStatus status;
    private final int sortOrder;
    private final Map<Language, SongTranslation> translations;
    private final List<SongChord> chords;
    private final List<SongSection> sections;
    private final Instant createdAt;
    private Instant updatedAt;

    public Song(UUID id, String guitarType, String difficulty, String source, String originalKey,
                int capoFret, Integer bpm, UUID strummingPatternId, boolean isFormula,
                PublicationStatus status, int sortOrder, Map<Language, SongTranslation> translations,
                List<SongChord> chords, List<SongSection> sections, Instant createdAt, Instant updatedAt) {
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
        this.translations = translations;
        this.chords = chords;
        this.sections = sections;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getGuitarType() { return guitarType; }
    public String getDifficulty() { return difficulty; }
    public String getSource() { return source; }
    public String getOriginalKey() { return originalKey; }
    public int getCapoFret() { return capoFret; }
    public Integer getBpm() { return bpm; }
    public UUID getStrummingPatternId() { return strummingPatternId; }
    public boolean isFormula() { return isFormula; }
    public PublicationStatus getStatus() { return status; }
    public int getSortOrder() { return sortOrder; }
    public Map<Language, SongTranslation> getTranslations() { return translations; }
    public List<SongChord> getChords() { return chords; }
    public List<SongSection> getSections() { return sections; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public String getTitle(Language language) {
        SongTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.title() : "";
    }

    public String getArtist(Language language) {
        SongTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.artist() : "";
    }

    public String getDescription(Language language) {
        SongTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.description() : "";
    }

    public record SongTranslation(String title, String artist, String description) {}
    public record SongChord(UUID id, UUID chordId, int sortOrder, boolean isRequired) {}
    public record SongSection(UUID id, String sectionType, int sectionOrder, String name,
                              String chordProgression, UUID strummingPatternId) {}
}
