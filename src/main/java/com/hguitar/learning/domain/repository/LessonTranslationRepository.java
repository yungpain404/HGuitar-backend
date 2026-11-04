package com.hguitar.learning.domain.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.domain.model.LessonTranslation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonTranslationRepository {

    LessonTranslation save(LessonTranslation translation);

    Optional<LessonTranslation> findByLessonIdAndLanguage(UUID lessonId, Language language);

    List<LessonTranslation> findByLessonId(UUID lessonId);

    /** Batch load translations for multiple lessons in one query. */
    List<LessonTranslation> findByLessonIdInAndLanguage(List<UUID> lessonIds, Language language);

    void deleteByLessonId(UUID lessonId);
}
