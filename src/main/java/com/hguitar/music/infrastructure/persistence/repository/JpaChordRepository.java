package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.music.domain.model.ChordCategory;
import com.hguitar.music.infrastructure.persistence.entity.ChordEntity;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaChordRepository extends JpaRepository<ChordEntity, UUID> {
    List<ChordEntity> findByGuitarTypeOrderByName(GuitarType guitarType);
    List<ChordEntity> findByCategoryOrderByName(ChordCategory category);
    List<ChordEntity> findAllByOrderByNameAsc();
}
