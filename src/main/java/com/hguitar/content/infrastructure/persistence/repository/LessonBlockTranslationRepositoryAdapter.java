package com.hguitar.content.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.content.domain.model.LessonBlockTranslation;
import com.hguitar.content.domain.repository.LessonBlockTranslationRepository;
import com.hguitar.content.infrastructure.persistence.entity.LessonBlockEntity;
import com.hguitar.content.infrastructure.persistence.entity.LessonBlockTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LessonBlockTranslationRepositoryAdapter implements LessonBlockTranslationRepository {

    private final JpaLessonBlockTranslationRepository jpa;
    private final JpaLessonBlockRepository blockJpa;

    public LessonBlockTranslationRepositoryAdapter(JpaLessonBlockTranslationRepository jpa,
                                                   JpaLessonBlockRepository blockJpa) {
        this.jpa = jpa;
        this.blockJpa = blockJpa;
    }

    @Override
    public LessonBlockTranslation save(LessonBlockTranslation t) {
        Optional<LessonBlockTranslationEntity> existing = jpa.findById(t.getId());
        LessonBlockTranslationEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setContent(t.getContent());
        } else {
            LessonBlockEntity blockEntity = blockJpa.findById(t.getBlockId())
                    .orElseThrow(() -> new IllegalStateException(
                            "LessonBlock not found: " + t.getBlockId()));
            entity = new LessonBlockTranslationEntity(t.getId(), blockEntity, t.getLanguage(), t.getContent());
        }
        LessonBlockTranslationEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<LessonBlockTranslation> findByBlockIdAndLanguage(UUID blockId, Language language) {
        return jpa.findByBlock_IdAndLanguage(blockId, language).map(this::toDomain);
    }

    @Override
    public List<LessonBlockTranslation> findByBlockId(UUID blockId) {
        return jpa.findByBlock_Id(blockId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteByBlockId(UUID blockId) {
        jpa.deleteByBlock_Id(blockId);
    }

    private LessonBlockTranslation toDomain(LessonBlockTranslationEntity e) {
        return new LessonBlockTranslation(
            e.getId(), e.getBlock().getId(), e.getLanguage(), e.getContent()
        );
    }
}
