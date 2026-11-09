package com.hguitar.music.application.dto.response;

import com.hguitar.music.domain.model.ChordCategory;
import com.hguitar.onboarding.domain.model.GuitarType;

import java.util.List;
import java.util.UUID;

public record ChordResponse(
        UUID id,
        String name,
        GuitarType guitarType,
        ChordCategory category,
        String description,
        List<ChordPositionResponse> positions
) {}
