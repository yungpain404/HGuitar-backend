package com.hguitar.learning.domain.repository;

import com.hguitar.learning.domain.model.LearningMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningMethodRepository {
    LearningMethod save(LearningMethod method);
    Optional<LearningMethod> findById(UUID id);
    List<LearningMethod> findAll();
    List<LearningMethod> findByDifficulty(String difficulty);
    List<LearningMethod> findPublished();
}
