package com.hguitar.song.application.dto.response;

import java.util.UUID;

public record SongChordResponse(
        UUID id,
        UUID chordId,
        String chordName,
        int sortOrder,
        boolean isRequired
) {}
