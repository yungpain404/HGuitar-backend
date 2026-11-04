package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.domain.model.LessonTranslation;
import com.hguitar.learning.domain.repository.LessonTranslationRepository;
import com.hguitar.learning.infrastructure.persistence.entity.LessonEntity;
import com.hguitar.learning.infrastructure.persistence.entity.LessonTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LessonTranslationRepositoryAdapter implements LessonTranslationRepository {

    private final JpaLessonTranslationRepository jpa;
    private final JpaLessonRepository lessonJpa;

    public LessonTranslationRepositoryAdapter(JpaLessonTranslationRepository jpa,
                                              JpaLessonRepository lessonJpa) {
        this.jpa = jpa;
        this.lessonJpa = lessonJpa;
    }

    @Override
    public LessonTranslation save(LessonTranslation translation) {
        // Find the existing entity by id if it already exists
        Optional<LessonTranslationEntity> existing = jpa.findById(translation.getId());
        LessonTranslationEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setTitle(translation.getTitle());
            entity.setDescription(translation.getDescription());
        } else {
            // Fetch the lesson entity for the ManyToOne reference
            LessonEntity lessonEntity = lessonJpa.findById(translation.getLessonId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Lesson not found: " + translation.getLessonId()));
            entity = new LessonTranslationEntity(
                    translation.getId(), lessonEntity,
                    translation.getLanguage(),
                    translation.getTitle(), translation.getDescription());
        }
        LessonTranslationEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<LessonTranslation> findByLessonIdAndLanguage(UUID lessonId, Language language) {
        return jpa.findByLesson_IdAndLanguage(lessonId, language)
                .map(this::toDomain);
    }

    @Override
    public List<LessonTranslation> findByLessonId(UUID lessonId) {
        return jpa.findByLesson_Id(lessonId).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<LessonTranslation> findByLessonIdInAndLanguage(List<UUID> lessonIds, Language language) {
        return jpa.findByLesson_IdInAndLanguage(lessonIds, language).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteByLessonId(UUID lessonId) {
        jpa.deleteByLesson_Id(lessonId);
    }

    private LessonTranslation toDomain(LessonTranslationEntity e) {
        return new LessonTranslation(
                e.getId(),
                e.getLesson().getId(),
                e.getLanguage(),
                e.getTitle(),
                e.getDescription()
        );
    }
}
