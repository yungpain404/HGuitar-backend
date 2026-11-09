package com.hguitar.song.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.music.domain.repository.ChordRepository;
import com.hguitar.song.application.dto.request.CreateSongRequest;
import com.hguitar.song.application.dto.response.SongChordResponse;
import com.hguitar.song.application.dto.response.SongResponse;
import com.hguitar.song.application.dto.response.SongSectionResponse;
import com.hguitar.song.domain.model.Song;
import com.hguitar.song.domain.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ChordRepository chordRepository;

    public SongService(SongRepository songRepository, ChordRepository chordRepository) {
        this.songRepository = songRepository;
        this.chordRepository = chordRepository;
    }

    @Transactional(readOnly = true)
    public List<SongResponse> listSongs(String source, String difficulty, String lang) {
        Language language = Language.fromCode(lang);
        List<Song> songs;
        if (source != null && difficulty != null) {
            songs = songRepository.findBySourceAndDifficulty(source, difficulty);
        } else if (source != null) {
            songs = songRepository.findBySource(source);
        } else if (difficulty != null) {
            songs = songRepository.findByDifficulty(difficulty);
        } else {
            songs = songRepository.findPublished();
        }
        return songs.stream()
                .filter(s -> s.getStatus() == PublicationStatus.PUBLISHED)
                .map(s -> toResponse(s, language))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SongResponse getSong(UUID id, String lang) {
        Language language = Language.fromCode(lang);
        Song song = songRepository.findById(id)
                .filter(s -> s.getStatus() == PublicationStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found."));
        return toResponse(song, language);
    }

    @Transactional
    public SongResponse createSong(CreateSongRequest request) {
        Map<Language, Song.SongTranslation> translations = request.translations().stream()
                .collect(Collectors.toMap(
                        ti -> Language.fromCode(ti.language()),
                        ti -> new Song.SongTranslation(ti.title(), ti.artist(), ti.description()),
                        (a, b) -> a));
        List<Song.SongChord> chords = request.chords().stream()
                .map(ci -> new Song.SongChord(UUID.randomUUID(), ci.chordId(), ci.sortOrder(), ci.isRequired()))
                .collect(Collectors.toList());
        List<Song.SongSection> sections = request.sections().stream()
                .map(si -> new Song.SongSection(UUID.randomUUID(), si.sectionType(), si.sectionOrder(),
                        si.name(), si.chordProgression(), si.strummingPatternId()))
                .collect(Collectors.toList());

        Song song = new Song(UUID.randomUUID(), request.guitarType(), request.difficulty(), request.source(),
                request.originalKey(), request.capoFret() != null ? request.capoFret() : 0,
                request.bpm(), request.strummingPatternId(), request.isFormula(),
                PublicationStatus.PUBLISHED, request.sortOrder(), translations, chords, sections,
                Instant.now(), null);

        return toResponse(songRepository.save(song), Language.EN);
    }

    private SongResponse toResponse(Song song, Language language) {
        List<SongChordResponse> chordResponses = song.getChords().stream()
                .map(c -> new SongChordResponse(c.id(), c.chordId(), chordName(c.chordId()),
                        c.sortOrder(), c.isRequired()))
                .collect(Collectors.toList());
        List<SongSectionResponse> sectionResponses = song.getSections().stream()
                .map(s -> new SongSectionResponse(s.id(), s.sectionType(), s.sectionOrder(),
                        s.name(), s.chordProgression(), s.strummingPatternId()))
                .collect(Collectors.toList());
        return new SongResponse(
                song.getId(), song.getGuitarType(), song.getDifficulty(), song.getSource(),
                song.getOriginalKey(), song.getCapoFret(), song.getBpm(), song.getStrummingPatternId(),
                song.isFormula(), song.getStatus().name(), song.getSortOrder(), song.getCreatedAt(),
                song.getTitle(language), song.getArtist(language), song.getDescription(language),
                chordResponses, sectionResponses);
    }

    private String chordName(UUID chordId) {
        return chordRepository.findById(chordId)
                .map(com.hguitar.music.domain.model.Chord::getName)
                .orElse("Unknown");
    }
}
