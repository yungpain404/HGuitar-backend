package com.hguitar.learning.infrastructure.persistence.mapper;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.model.LessonTranslation;
import com.hguitar.learning.infrastructure.persistence.entity.LessonEntity;
import com.hguitar.learning.infrastructure.persistence.entity.LessonTranslationEntity;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class LessonEntityMapper {

    public LessonEntity toEntity(Lesson domain) {
        LessonEntity entity = new LessonEntity(
            domain.getId(),
            domain.getModuleId(),
            domain.getSortOrder(),
            domain.getDurationMinutes(),
            domain.getStatus()
        );
        return entity;
    }

    public Lesson toDomain(LessonEntity entity) {
        Map<Language, Lesson.LessonTranslation> translations = new EnumMap<>(Language.class);
        if (entity.getTranslations() != null) {
            for (LessonTranslationEntity t : entity.getTranslations()) {
                translations.put(t.getLanguage(),
                    new Lesson.LessonTranslation(t.getTitle(), t.getDescription()));
            }
        }
        return new Lesson(
            entity.getId(),
            entity.getModuleId(),
            entity.getSortOrder(),
            entity.getDurationMinutes(),
            entity.getStatus(),
            translations,
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public LessonTranslationEntity toTranslationEntity(LessonTranslation domain) {
        // LessonTranslationEntity requires a LessonEntity reference for the ManyToOne.
        // This mapper variant is only used when we have the full entity context.
        // Callers should use the full (id, lesson, language, title, desc) constructor directly.
        throw new UnsupportedOperationException(
            "Use LessonTranslationEntity(UUID, LessonEntity, Language, String, String) directly.");
    }

    public LessonTranslation toTranslationDomain(LessonTranslationEntity entity) {
        return new LessonTranslation(
            entity.getId(),
            entity.getLesson().getId(),
            entity.getLanguage(),
            entity.getTitle(),
            entity.getDescription()
        );
    }
}
