package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.music.domain.model.StrummingPattern;
import com.hguitar.music.domain.repository.StrummingPatternRepository;
import com.hguitar.music.infrastructure.persistence.entity.StrummingPatternEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StrummingPatternRepositoryAdapter implements StrummingPatternRepository {

    private final JpaStrummingPatternRepository jpa;

    public StrummingPatternRepositoryAdapter(JpaStrummingPatternRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public StrummingPattern save(StrummingPattern pattern) {
        StrummingPatternEntity entity = jpa.findById(pattern.getId())
                .orElse(new StrummingPatternEntity(pattern.getId(), pattern.getName(),
                        pattern.getFormula(), pattern.getDescription(), pattern.getDifficulty(),
                        pattern.getGuitarType(), pattern.getBpmMin(), pattern.getBpmMax(),
                        pattern.isFormula(), pattern.getStatus(), pattern.getSortOrder()));
        entity.setName(pattern.getName());
        entity.setFormula(pattern.getFormula());
        entity.setDescription(pattern.getDescription());
        entity.setDifficulty(pattern.getDifficulty());
        entity.setGuitarType(pattern.getGuitarType());
        entity.setBpmMin(pattern.getBpmMin());
        entity.setBpmMax(pattern.getBpmMax());
        entity.setFormula(pattern.isFormula());
        entity.setStatus(pattern.getStatus());
        entity.setSortOrder(pattern.getSortOrder());
        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<StrummingPattern> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<StrummingPattern> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<StrummingPattern> findByGuitarType(String guitarType) {
        return jpa.findByGuitarTypeOrderBySortOrderAsc(guitarType).stream().map(this::toDomain).toList();
    }

    @Override
    public List<StrummingPattern> findByDifficulty(String difficulty) {
        return jpa.findByDifficultyOrderBySortOrderAsc(difficulty).stream().map(this::toDomain).toList();
    }

    @Override
    public List<StrummingPattern> findPublished() {
        return jpa.findByStatusOrderBySortOrderAsc(PublicationStatus.PUBLISHED).stream().map(this::toDomain).toList();
    }

    private StrummingPattern toDomain(StrummingPatternEntity e) {
        return new StrummingPattern(e.getId(), e.getName(), e.getFormula(), e.getDescription(),
                e.getDifficulty(), e.getGuitarType(), e.getBpmMin(), e.getBpmMax(),
                e.isFormula(), e.getStatus(), e.getSortOrder(), e.getCreatedAt());
    }
}
