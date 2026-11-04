package com.hguitar.music.application.dto.response;

import java.util.UUID;

public record ChordPositionResponse(
        UUID id,
        int positionNumber,
        int baseFret,
        String fingersJson
) {}
