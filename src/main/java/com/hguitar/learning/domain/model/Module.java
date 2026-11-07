package com.hguitar.learning.domain.model;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Module {

    private final UUID id;
    private final UUID courseId;
    private int sortOrder;
    private PublicationStatus status;
    private final Map<Language, ModuleTranslation> translations;
    private final Instant createdAt;
    private Instant updatedAt;

    public Module(UUID id, UUID courseId, int sortOrder, PublicationStatus status,
                  Map<Language, ModuleTranslation> translations, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.courseId = courseId;
        this.sortOrder = sortOrder;
        this.status = status;
        this.translations = translations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public UUID getCourseId() { return courseId; }
    public int getSortOrder() { return sortOrder; }
    public PublicationStatus getStatus() { return status; }
    public Map<Language, ModuleTranslation> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public String getTitle(Language language) {
        ModuleTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.title() : "";
    }

    public String getDescription(Language language) {
        ModuleTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.description() : "";
    }

    public record ModuleTranslation(String title, String description) {}
}
