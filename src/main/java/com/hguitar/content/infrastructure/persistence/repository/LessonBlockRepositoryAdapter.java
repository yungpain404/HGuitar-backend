package com.hguitar.content.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.content.domain.model.LessonBlock;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import com.hguitar.content.infrastructure.persistence.entity.LessonBlockEntity;
import com.hguitar.content.infrastructure.persistence.entity.LessonBlockTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LessonBlockRepositoryAdapter implements LessonBlockRepository {

    private final JpaLessonBlockRepository jpa;

    public LessonBlockRepositoryAdapter(JpaLessonBlockRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public LessonBlock save(LessonBlock block) {
        LessonBlockEntity entity = jpa.findById(block.getId())
                .orElse(new LessonBlockEntity(block.getId(), block.getLessonId(),
                        block.getBlockType(), block.getSortOrder(),
                        block.getMediaAssetId(), block.getMetadataJson()));

        entity.setSortOrder(block.getSortOrder());
        entity.setMediaAssetId(block.getMediaAssetId());
        entity.setMetadataJson(block.getMetadataJson());

        entity.getTranslations().clear();
        block.getTranslations().forEach((lang, content) ->
                entity.getTranslations().add(
                        new LessonBlockTranslationEntity(UUID.randomUUID(), entity, lang, content)));

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<LessonBlock> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<LessonBlock> findByLessonId(UUID lessonId) {
        return jpa.findByLessonIdOrderBySortOrder(lessonId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<LessonBlock> findByLessonIdOrderBySortOrder(UUID lessonId) {
        return jpa.findByLessonIdOrderBySortOrder(lessonId).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByLessonIdAndSortOrderAndIdNot(UUID lessonId, int sortOrder, UUID excludedId) {
        return jpa.existsByLessonIdAndSortOrderAndIdNot(lessonId, sortOrder, excludedId);
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public void delete(UUID id) {
        jpa.deleteById(id);
    }

    private LessonBlock toDomain(LessonBlockEntity e) {
        Map<Language, String> translations = new EnumMap<>(Language.class);
        for (LessonBlockTranslationEntity t : e.getTranslations()) {
            translations.put(t.getLanguage(), t.getContent());
        }
        return new LessonBlock(e.getId(), e.getLessonId(), e.getBlockType(), e.getSortOrder(),
                e.getMediaAssetId(), e.getMetadataJson(), translations,
                e.getCreatedAt(), e.getUpdatedAt());
    }
}
