package com.hguitar.content.application.dto.request;

import com.hguitar.content.domain.model.BlockType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateLessonBlockRequest(

    @NotNull(message = "blockType is required")
    BlockType blockType,

    @Min(value = 1, message = "sortOrder must be >= 1")
    int sortOrder,

    UUID mediaAssetId,

    @Size(max = 4000, message = "metadataJson must not exceed 4000 characters")
    String metadataJson,

    @Size(max = 50000, message = "contentVi must not exceed 50000 characters")
    String contentVi,

    @Size(max = 50000, message = "contentEn must not exceed 50000 characters")
    String contentEn
) {}
