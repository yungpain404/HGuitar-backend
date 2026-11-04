package com.hguitar.content.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.content.infrastructure.persistence.entity.LessonBlockTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaLessonBlockTranslationRepository extends JpaRepository<LessonBlockTranslationEntity, UUID> {

    Optional<LessonBlockTranslationEntity> findByBlock_IdAndLanguage(UUID blockId, Language language);

    List<LessonBlockTranslationEntity> findByBlock_Id(UUID blockId);

    void deleteByBlock_Id(UUID blockId);
}
