package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.infrastructure.persistence.entity.LessonEntity;
import com.hguitar.learning.infrastructure.persistence.entity.LessonTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LessonRepositoryAdapter implements LessonRepository {

    private final JpaLessonRepository jpa;

    public LessonRepositoryAdapter(JpaLessonRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = jpa.findById(lesson.getId())
                .orElse(new LessonEntity(lesson.getId(), lesson.getModuleId(),
                        lesson.getSortOrder(), lesson.getDurationMinutes(), lesson.getStatus()));

        entity.setSortOrder(lesson.getSortOrder());
        entity.setDurationMinutes(lesson.getDurationMinutes());
        entity.setStatus(lesson.getStatus());

        entity.getTranslations().clear();
        lesson.getTranslations().forEach((lang, t) ->
                entity.getTranslations().add(new LessonTranslationEntity(
                        UUID.randomUUID(), entity, lang, t.title(), t.description())));

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Lesson> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Lesson> findAllByIds(Collection<UUID> ids) {
        if (ids.isEmpty()) return List.of();
        return jpa.findAllById(ids).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Lesson> findByModuleId(UUID moduleId) {
        return jpa.findByModuleIdOrderBySortOrder(moduleId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Lesson> findByModuleIdOrderBySortOrder(UUID moduleId) {
        return jpa.findByModuleIdOrderBySortOrder(moduleId).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByModuleIdAndSortOrderAndIdNot(UUID moduleId, int sortOrder, UUID excludedId) {
        return jpa.existsByModuleIdAndSortOrderAndIdNot(moduleId, sortOrder, excludedId);
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    private Lesson toDomain(LessonEntity e) {
        Map<Language, Lesson.LessonTranslation> translations = new EnumMap<>(Language.class);
        for (LessonTranslationEntity t : e.getTranslations()) {
            translations.put(t.getLanguage(), new Lesson.LessonTranslation(t.getTitle(), t.getDescription()));
        }
        return new Lesson(e.getId(), e.getModuleId(), e.getSortOrder(), e.getDurationMinutes(),
                e.getStatus(), translations, e.getCreatedAt(), e.getUpdatedAt());
    }
}
