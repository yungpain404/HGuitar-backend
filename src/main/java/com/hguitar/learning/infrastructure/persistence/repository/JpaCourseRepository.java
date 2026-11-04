package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.infrastructure.persistence.entity.CourseEntity;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, UUID> {
    List<CourseEntity> findByStatusOrderBySortOrder(PublicationStatus status);
    List<CourseEntity> findByGuitarTypeAndStatusOrderBySortOrder(GuitarType guitarType, PublicationStatus status);
    List<CourseEntity> findAllByOrderBySortOrder();
}
