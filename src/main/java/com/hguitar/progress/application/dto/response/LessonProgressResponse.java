package com.hguitar.progress.application.dto.response;

import com.hguitar.progress.domain.model.LessonProgress;

import java.time.Instant;
import java.util.UUID;

public record LessonProgressResponse(
        UUID lessonId,
        String title,
        LessonProgress.Status status,
        Instant completedAt
) {}
