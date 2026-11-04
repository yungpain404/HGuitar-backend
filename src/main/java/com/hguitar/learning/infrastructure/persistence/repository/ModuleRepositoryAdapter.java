package com.hguitar.learning.infrastructure.persistence.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.learning.domain.model.Module;
import com.hguitar.learning.domain.repository.ModuleRepository;
import com.hguitar.learning.infrastructure.persistence.entity.ModuleEntity;
import com.hguitar.learning.infrastructure.persistence.entity.ModuleTranslationEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ModuleRepositoryAdapter implements ModuleRepository {

    private final JpaModuleRepository jpa;

    public ModuleRepositoryAdapter(JpaModuleRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Module save(Module module) {
        ModuleEntity entity = jpa.findById(module.getId())
                .orElse(new ModuleEntity(module.getId(), module.getCourseId(),
                        module.getSortOrder(), module.getStatus()));

        entity.setSortOrder(module.getSortOrder());
        entity.setStatus(module.getStatus());

        entity.getTranslations().clear();
        module.getTranslations().forEach((lang, t) ->
                entity.getTranslations().add(new ModuleTranslationEntity(
                        UUID.randomUUID(), entity, lang, t.title(), t.description())));

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Module> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpa.existsById(id);
    }

    @Override
    public List<Module> findByCourseIdOrderBySortOrder(UUID courseId) {
        return jpa.findByCourseIdOrderBySortOrder(courseId).stream().map(this::toDomain).toList();
    }

    private Module toDomain(ModuleEntity e) {
        Map<Language, Module.ModuleTranslation> translations = new EnumMap<>(Language.class);
        for (ModuleTranslationEntity t : e.getTranslations()) {
            translations.put(t.getLanguage(), new Module.ModuleTranslation(t.getTitle(), t.getDescription()));
        }
        return new Module(e.getId(), e.getCourseId(), e.getSortOrder(), e.getStatus(),
                translations, e.getCreatedAt(), e.getUpdatedAt());
    }
}
