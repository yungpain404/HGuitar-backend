package com.hguitar.assessment.infrastructure.persistence.repository;

import com.hguitar.assessment.infrastructure.persistence.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaQuizRepository extends JpaRepository<QuizEntity, UUID> {
    Optional<QuizEntity> findByLessonId(UUID lessonId);
}
