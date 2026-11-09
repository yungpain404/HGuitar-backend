package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.domain.model.LearningMethod;
import com.hguitar.learning.domain.repository.LearningMethodRepository;
import com.hguitar.learning.infrastructure.persistence.entity.LearningMethodEntity;
import com.hguitar.learning.infrastructure.persistence.entity.LearningMethodTranslationEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LearningMethodRepositoryAdapter implements LearningMethodRepository {

    private final JpaLearningMethodRepository jpa;
    private final ObjectMapper objectMapper;

    public LearningMethodRepositoryAdapter(JpaLearningMethodRepository jpa, ObjectMapper objectMapper) {
        this.jpa = jpa;
        this.objectMapper = objectMapper;
    }

    @Override
    public LearningMethod save(LearningMethod method) {
        LearningMethodEntity entity = jpa.findById(method.getId())
                .orElse(new LearningMethodEntity(method.getId(), method.getNameKey(), method.getDifficulty(),
                        method.getTargetGuitar(), method.getEstimatedWeeks(), method.getStepsCount(),
                        method.getStatus(), method.getSortOrder()));
        entity.setNameKey(method.getNameKey());
        entity.setDifficulty(method.getDifficulty());
        entity.setTargetGuitar(method.getTargetGuitar());
        entity.setEstimatedWeeks(method.getEstimatedWeeks());
        entity.setStepsCount(method.getStepsCount());
        entity.setStatus(method.getStatus());
        entity.setSortOrder(method.getSortOrder());

        entity.getTranslations().clear();
        for (var entry : method.getTranslations().entrySet()) {
            Language lang = entry.getKey();
            LearningMethod.LearningMethodTranslation t = entry.getValue();
            try {
                entity.getTranslations().add(new LearningMethodTranslationEntity(UUID.randomUUID(), entity, lang,
                        t.title(), t.description(), objectMapper.writeValueAsString(t.steps())));
            } catch (Exception e) {
                throw new IllegalStateException("Failed to serialize steps", e);
            }
        }

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<LearningMethod> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<LearningMethod> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<LearningMethod> findByDifficulty(String difficulty) {
        return jpa.findByDifficultyOrderBySortOrderAsc(difficulty).stream().map(this::toDomain).toList();
    }

    @Override
    public List<LearningMethod> findPublished() {
        return jpa.findByStatusOrderBySortOrderAsc(PublicationStatus.PUBLISHED).stream().map(this::toDomain).toList();
    }

    private LearningMethod toDomain(LearningMethodEntity e) {
        Map<Language, LearningMethod.LearningMethodTranslation> translations = e.getTranslations().stream()
                .collect(Collectors.toMap(
                        LearningMethodTranslationEntity::getLanguage,
                        te -> {
                            try {
                                @SuppressWarnings("unchecked")
                                List<String> steps = objectMapper.readValue(te.getStepsJson(),
                                        objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                                return new LearningMethod.LearningMethodTranslation(te.getTitle(), te.getDescription(), steps);
                            } catch (Exception ex) {
                                throw new IllegalStateException("Failed to deserialize steps", ex);
                            }
                        },
                        (a, b) -> a));
        return new LearningMethod(e.getId(), e.getNameKey(), e.getDifficulty(), e.getTargetGuitar(),
                e.getEstimatedWeeks(), e.getStepsCount(), e.getStatus(), e.getSortOrder(),
                translations, e.getCreatedAt(), e.getUpdatedAt());
    }
}
