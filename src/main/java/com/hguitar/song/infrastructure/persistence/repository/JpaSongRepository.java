package com.hguitar.song.infrastructure.persistence.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.song.infrastructure.persistence.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaSongRepository extends JpaRepository<SongEntity, UUID> {
    List<SongEntity> findBySourceAndDifficultyOrderBySortOrderAsc(String source, String difficulty);
    List<SongEntity> findBySourceOrderBySortOrderAsc(String source);
    List<SongEntity> findByDifficultyOrderBySortOrderAsc(String difficulty);
    List<SongEntity> findByStatusOrderBySortOrderAsc(PublicationStatus status);
}
