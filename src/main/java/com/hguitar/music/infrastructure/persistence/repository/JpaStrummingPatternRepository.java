package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.music.infrastructure.persistence.entity.StrummingPatternEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaStrummingPatternRepository extends JpaRepository<StrummingPatternEntity, UUID> {
    List<StrummingPatternEntity> findByGuitarTypeOrderBySortOrderAsc(String guitarType);
    List<StrummingPatternEntity> findByDifficultyOrderBySortOrderAsc(String difficulty);
    List<StrummingPatternEntity> findByStatusOrderBySortOrderAsc(PublicationStatus status);
}
