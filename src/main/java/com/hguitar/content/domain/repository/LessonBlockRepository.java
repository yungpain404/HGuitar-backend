package com.hguitar.content.domain.repository;

import com.hguitar.content.domain.model.LessonBlock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonBlockRepository {
    LessonBlock save(LessonBlock block);
    Optional<LessonBlock> findById(UUID id);
    List<LessonBlock> findByLessonId(UUID lessonId);
    List<LessonBlock> findByLessonIdOrderBySortOrder(UUID lessonId);
    boolean existsByLessonIdAndSortOrderAndIdNot(UUID lessonId, int sortOrder, UUID excludedId);
    void deleteById(UUID id);
    void delete(UUID id);
}
