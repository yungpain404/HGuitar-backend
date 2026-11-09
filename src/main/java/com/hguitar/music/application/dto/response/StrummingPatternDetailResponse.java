package com.hguitar.music.application.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Chi tiết 1 công thức đánh, kèm theo danh sách hướng dẫn từng bước (instructions).
 * Dùng cho endpoint GET /api/v1/strumming-patterns/{id}.
 */
public record StrummingPatternDetailResponse(
        UUID id,
        String name,
        String formula,
        String description,
        String difficulty,
        String guitarType,
        Integer bpmMin,
        Integer bpmMax,
        boolean isFormula,
        String status,
        int sortOrder,
        Instant createdAt,
        List<StrummingInstructionResponse> instructions
) {}
