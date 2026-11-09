package com.hguitar.song.application.dto.request;

import java.util.List;
import java.util.UUID;

public record CreateSongRequest(
        String guitarType,
        String difficulty,
        String source,
        String originalKey,
        Integer capoFret,
        Integer bpm,
        UUID strummingPatternId,
        boolean isFormula,
        int sortOrder,
        List<SongTranslationInput> translations,
        List<SongChordInput> chords,
        List<SongSectionInput> sections
) {

    public record SongTranslationInput(String language, String title, String artist, String description) {}
    public record SongChordInput(UUID chordId, int sortOrder, boolean isRequired) {}
    public record SongSectionInput(String sectionType, int sectionOrder, String name,
                                   String chordProgression, UUID strummingPatternId) {}
}
