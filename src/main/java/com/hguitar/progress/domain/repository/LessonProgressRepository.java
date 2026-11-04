package com.hguitar.progress.domain.repository;

import com.hguitar.progress.domain.model.LessonProgress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonProgressRepository {
    LessonProgress save(LessonProgress progress);
    Optional<LessonProgress> findByUserIdAndLessonId(UUID userId, UUID lessonId);
    List<LessonProgress> findByUserId(UUID userId);
}
