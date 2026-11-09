package com.hguitar.music.application.dto.response;

import java.util.UUID;

/**
 * Một bước hướng dẫn đánh chi tiết, đã được dịch theo ngôn ngữ yêu cầu.
 */
public record StrummingInstructionResponse(
        UUID id,
        int stepNumber,
        String title,
        String content,
        String tip
) {}
