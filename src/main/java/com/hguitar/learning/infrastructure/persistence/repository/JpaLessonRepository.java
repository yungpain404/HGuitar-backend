package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.learning.infrastructure.persistence.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaLessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findByModuleIdOrderBySortOrder(UUID moduleId);

    @Query("SELECT COUNT(l) > 0 FROM LessonEntity l WHERE l.moduleId = :moduleId AND l.sortOrder = :sortOrder AND l.id <> :excludedId")
    boolean existsByModuleIdAndSortOrderAndIdNot(@Param("moduleId") UUID moduleId,
                                                  @Param("sortOrder") int sortOrder,
                                                  @Param("excludedId") UUID excludedId);
}
