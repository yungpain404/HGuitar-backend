package com.hguitar.learning.domain.repository;

import com.hguitar.learning.domain.model.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository {
    Module save(Module module);
    Optional<Module> findById(UUID id);
    boolean existsById(UUID id);
    List<Module> findByCourseIdOrderBySortOrder(UUID courseId);
}
