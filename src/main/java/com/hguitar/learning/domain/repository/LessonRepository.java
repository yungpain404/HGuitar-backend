package com.hguitar.learning.domain.repository;

import com.hguitar.learning.domain.model.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository {
    Lesson save(Lesson lesson);
    Optional<Lesson> findById(UUID id);
    List<Lesson> findByModuleId(UUID moduleId);
    List<Lesson> findByModuleIdOrderBySortOrder(UUID moduleId);
    boolean existsByModuleIdAndSortOrderAndIdNot(UUID moduleId, int sortOrder, UUID excludedId);
    void deleteById(UUID id);
}
