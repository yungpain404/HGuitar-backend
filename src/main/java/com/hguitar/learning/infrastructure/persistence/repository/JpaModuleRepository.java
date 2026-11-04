package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.learning.infrastructure.persistence.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaModuleRepository extends JpaRepository<ModuleEntity, UUID> {
    List<ModuleEntity> findByCourseIdOrderBySortOrder(UUID courseId);
}
