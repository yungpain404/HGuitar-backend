package com.hguitar.content.infrastructure.persistence.repository;

import com.hguitar.content.infrastructure.persistence.entity.LessonBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaLessonBlockRepository extends JpaRepository<LessonBlockEntity, UUID> {
    List<LessonBlockEntity> findByLessonIdOrderBySortOrder(UUID lessonId);

    @Query("SELECT COUNT(b) > 0 FROM LessonBlockEntity b WHERE b.lessonId = :lessonId AND b.sortOrder = :sortOrder AND b.id <> :excludedId")
    boolean existsByLessonIdAndSortOrderAndIdNot(@Param("lessonId") UUID lessonId,
                                                  @Param("sortOrder") int sortOrder,
                                                  @Param("excludedId") UUID excludedId);
}
