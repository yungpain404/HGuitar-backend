package com.hguitar.progress.infrastructure.persistence.repository;

import com.hguitar.progress.infrastructure.persistence.entity.LessonProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaLessonProgressRepository extends JpaRepository<LessonProgressEntity, UUID> {
    Optional<LessonProgressEntity> findByUserIdAndLessonId(UUID userId, UUID lessonId);
    List<LessonProgressEntity> findByUserId(UUID userId);
}
