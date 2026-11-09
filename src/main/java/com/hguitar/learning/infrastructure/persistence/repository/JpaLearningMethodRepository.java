package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.infrastructure.persistence.entity.LearningMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaLearningMethodRepository extends JpaRepository<LearningMethodEntity, UUID> {
    List<LearningMethodEntity> findByDifficultyOrderBySortOrderAsc(String difficulty);
    List<LearningMethodEntity> findByStatusOrderBySortOrderAsc(PublicationStatus status);
}
