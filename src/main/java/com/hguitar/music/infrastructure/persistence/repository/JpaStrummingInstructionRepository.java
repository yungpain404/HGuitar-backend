package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.music.infrastructure.persistence.entity.StrummingInstructionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaStrummingInstructionRepository extends JpaRepository<StrummingInstructionEntity, UUID> {
    List<StrummingInstructionEntity> findByPatternIdOrderByStepNumberAsc(UUID patternId);
}
