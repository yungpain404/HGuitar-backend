package com.hguitar.music.application.dto.response;

import com.hguitar.onboarding.domain.model.GuitarType;

import java.util.List;
import java.util.UUID;

public record ChordResponse(
        UUID id,
        String name,
        GuitarType guitarType,
        String description,
        List<ChordPositionResponse> positions
) {}
