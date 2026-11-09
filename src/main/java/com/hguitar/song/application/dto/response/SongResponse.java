package com.hguitar.song.application.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record SongResponse(
        UUID id,
        String guitarType,
        String difficulty,
        String source,
        String originalKey,
        int capoFret,
        Integer bpm,
        UUID strummingPatternId,
        boolean isFormula,
        String status,
        int sortOrder,
        Instant createdAt,
        String title,
        String artist,
        String description,
        List<SongChordResponse> chords,
        List<SongSectionResponse> sections
) {}
