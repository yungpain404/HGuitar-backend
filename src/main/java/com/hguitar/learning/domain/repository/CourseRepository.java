package com.hguitar.learning.domain.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.domain.model.Course;
import com.hguitar.onboarding.domain.model.GuitarType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findById(UUID id);
    List<Course> findByStatus(PublicationStatus status);
    List<Course> findByGuitarTypeAndStatus(GuitarType guitarType, PublicationStatus status);
    List<Course> findAll();
}
