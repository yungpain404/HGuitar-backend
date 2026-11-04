package com.hguitar.content.infrastructure.persistence;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.content.domain.service.LessonStatusChecker;
import com.hguitar.learning.infrastructure.persistence.repository.JpaLessonRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Infrastructure adapter that reads lesson status from the learning schema
 * without importing any Learning domain model into the Content domain.
 */
@Component
public class LessonStatusCheckerAdapter implements LessonStatusChecker {

    private final JpaLessonRepository jpaLessonRepository;

    public LessonStatusCheckerAdapter(JpaLessonRepository jpaLessonRepository) {
        this.jpaLessonRepository = jpaLessonRepository;
    }

    @Override
    public Optional<PublicationStatus> getLessonStatus(UUID lessonId) {
        return jpaLessonRepository.findById(lessonId)
                .map(e -> e.getStatus());
    }
}
