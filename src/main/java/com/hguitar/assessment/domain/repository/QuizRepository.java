package com.hguitar.assessment.domain.repository;

import com.hguitar.assessment.domain.model.Quiz;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository {
    Optional<Quiz> findById(UUID id);
    Optional<Quiz> findByLessonId(UUID lessonId);
}
