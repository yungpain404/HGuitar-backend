package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.infrastructure.persistence.entity.LessonTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaLessonTranslationRepository extends JpaRepository<LessonTranslationEntity, UUID> {

    Optional<LessonTranslationEntity> findByLesson_IdAndLanguage(UUID lessonId, Language language);

    List<LessonTranslationEntity> findByLesson_Id(UUID lessonId);

    List<LessonTranslationEntity> findByLesson_IdInAndLanguage(List<UUID> lessonIds, Language language);

    void deleteByLesson_Id(UUID lessonId);
}
