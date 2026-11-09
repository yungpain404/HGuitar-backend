package com.hguitar.song.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.song.domain.model.Song;
import com.hguitar.song.domain.repository.SongRepository;
import com.hguitar.song.infrastructure.persistence.entity.SongChordEntity;
import com.hguitar.song.infrastructure.persistence.entity.SongEntity;
import com.hguitar.song.infrastructure.persistence.entity.SongSectionEntity;
import com.hguitar.song.infrastructure.persistence.entity.SongTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SongRepositoryAdapter implements SongRepository {

    private final JpaSongRepository jpa;

    public SongRepositoryAdapter(JpaSongRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Song save(Song song) {
        SongEntity entity = jpa.findById(song.getId())
                .orElse(new SongEntity(song.getId(), song.getGuitarType(), song.getDifficulty(),
                        song.getSource(), song.getOriginalKey(), song.getCapoFret(), song.getBpm(),
                        song.getStrummingPatternId(), song.isFormula(), song.getStatus(), song.getSortOrder()));
        entity.setGuitarType(song.getGuitarType());
        entity.setDifficulty(song.getDifficulty());
        entity.setSource(song.getSource());
        entity.setOriginalKey(song.getOriginalKey());
        entity.setCapoFret(song.getCapoFret());
        entity.setBpm(song.getBpm());
        entity.setStrummingPatternId(song.getStrummingPatternId());
        entity.setFormula(song.isFormula());
        entity.setStatus(song.getStatus());
        entity.setSortOrder(song.getSortOrder());

        // Replace collections
        entity.getTranslations().clear();
        entity.getChords().clear();
        entity.getSections().clear();

        for (var entry : song.getTranslations().entrySet()) {
            Language lang = entry.getKey();
            Song.SongTranslation t = entry.getValue();
            entity.getTranslations().add(new SongTranslationEntity(UUID.randomUUID(), entity, lang,
                    t.title(), t.artist(), t.description()));
        }
        for (var c : song.getChords()) {
            entity.getChords().add(new SongChordEntity(UUID.randomUUID(), entity, c.chordId(), c.sortOrder(), c.isRequired()));
        }
        for (var s : song.getSections()) {
            entity.getSections().add(new SongSectionEntity(UUID.randomUUID(), entity, s.sectionType(), s.sectionOrder(),
                    s.name(), s.chordProgression(), s.strummingPatternId()));
        }

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Song> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Song> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Song> findBySourceAndDifficulty(String source, String difficulty) {
        return jpa.findBySourceAndDifficultyOrderBySortOrderAsc(source, difficulty).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Song> findBySource(String source) {
        return jpa.findBySourceOrderBySortOrderAsc(source).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Song> findByDifficulty(String difficulty) {
        return jpa.findByDifficultyOrderBySortOrderAsc(difficulty).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Song> findPublished() {
        return jpa.findByStatusOrderBySortOrderAsc(PublicationStatus.PUBLISHED).stream().map(this::toDomain).toList();
    }

    private Song toDomain(SongEntity e) {
        Map<Language, Song.SongTranslation> translations = e.getTranslations().stream()
                .collect(Collectors.toMap(
                        SongTranslationEntity::getLanguage,
                        te -> new Song.SongTranslation(te.getTitle(), te.getArtist(), te.getDescription()),
                        (a, b) -> a));
        List<Song.SongChord> chords = e.getChords().stream()
                .map(ce -> new Song.SongChord(ce.getId(), ce.getChordId(), ce.getSortOrder(), ce.isRequired()))
                .toList();
        List<Song.SongSection> sections = e.getSections().stream()
                .map(se -> new Song.SongSection(se.getId(), se.getSectionType(), se.getSectionOrder(),
                        se.getName(), se.getChordProgression(), se.getStrummingPatternId()))
                .toList();
        return new Song(e.getId(), e.getGuitarType(), e.getDifficulty(), e.getSource(), e.getOriginalKey(),
                e.getCapoFret(), e.getBpm(), e.getStrummingPatternId(), e.isFormula(), e.getStatus(),
                e.getSortOrder(), translations, chords, sections, e.getCreatedAt(), e.getUpdatedAt());
    }
}
