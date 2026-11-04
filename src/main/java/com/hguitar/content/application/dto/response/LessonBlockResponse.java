package com.hguitar.content.application.dto.response;

import com.hguitar.content.domain.model.BlockType;

import java.util.UUID;

public record LessonBlockResponse(
        UUID id,
        UUID lessonId,
        BlockType blockType,
        int sortOrder,
        UUID mediaAssetId,
        String metadataJson,
        String content
) {}
