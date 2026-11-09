package com.hguitar.song.application.dto.response;

import java.util.UUID;

public record SongSectionResponse(
        UUID id,
        String sectionType,
        int sectionOrder,
        String name,
        String chordProgression,
        UUID strummingPatternId
) {}
