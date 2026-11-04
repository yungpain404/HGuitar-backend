package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.domain.model.Course;
import com.hguitar.learning.domain.repository.CourseRepository;
import com.hguitar.learning.infrastructure.persistence.entity.CourseEntity;
import com.hguitar.learning.infrastructure.persistence.entity.CourseTranslationEntity;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CourseRepositoryAdapter implements CourseRepository {

    private final JpaCourseRepository jpa;

    public CourseRepositoryAdapter(JpaCourseRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = jpa.findById(course.getId())
                .orElse(new CourseEntity(course.getId(), course.getGuitarType(),
                        course.getSortOrder(), course.getStatus()));

        entity.setSortOrder(course.getSortOrder());
        entity.setStatus(course.getStatus());

        // Sync translations
        entity.getTranslations().clear();
        course.getTranslations().forEach((lang, t) ->
                entity.getTranslations().add(new CourseTranslationEntity(
                        UUID.randomUUID(), entity, lang, t.title(), t.description())));

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Course> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Course> findByStatus(PublicationStatus status) {
        return jpa.findByStatusOrderBySortOrder(status).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Course> findByGuitarTypeAndStatus(GuitarType guitarType, PublicationStatus status) {
        return jpa.findByGuitarTypeAndStatusOrderBySortOrder(guitarType, status)
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Course> findAll() {
        return jpa.findAllByOrderBySortOrder().stream().map(this::toDomain).toList();
    }

    private Course toDomain(CourseEntity e) {
        Map<Language, Course.CourseTranslation> translations = new EnumMap<>(Language.class);
        for (CourseTranslationEntity t : e.getTranslations()) {
            translations.put(t.getLanguage(), new Course.CourseTranslation(t.getTitle(), t.getDescription()));
        }
        return new Course(e.getId(), e.getGuitarType(), e.getSortOrder(), e.getStatus(),
                translations, e.getCreatedAt(), e.getUpdatedAt());
    }
}
