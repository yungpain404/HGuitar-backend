package com.hguitar.assessment.domain.repository;

import com.hguitar.assessment.domain.model.QuizAttempt;

import java.util.Optional;
import java.util.UUID;

public interface QuizAttemptRepository {
    QuizAttempt save(QuizAttempt attempt);
    Optional<QuizAttempt> findById(UUID id);
    int countByQuizIdAndUserId(UUID quizId, UUID userId);
}
