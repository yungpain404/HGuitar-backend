package com.hguitar.assessment.infrastructure.persistence.repository;

import com.hguitar.assessment.infrastructure.persistence.entity.QuizAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaQuizAttemptRepository extends JpaRepository<QuizAttemptEntity, UUID> {
    int countByQuizIdAndUserId(UUID quizId, UUID userId);
}
