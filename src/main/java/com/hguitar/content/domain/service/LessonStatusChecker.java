package com.hguitar.content.domain.service;

import com.hguitar.common.domain.PublicationStatus;

import java.util.Optional;
import java.util.UUID;

/**
 * Abstraction used by the Content context to query lesson status
 * without importing from the Learning domain model directly.
 *
 * Implemented in infrastructure by querying learning.lessons.
 */
public interface LessonStatusChecker {

    Optional<PublicationStatus> getLessonStatus(UUID lessonId);

    default boolean lessonExists(UUID lessonId) {
        return getLessonStatus(lessonId).isPresent();
    }
}
