package com.hguitar.learning.domain.model;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Lesson {

    private final UUID id;
    private final UUID moduleId;
    private int sortOrder;
    private int durationMinutes;
    private PublicationStatus status;
    private final Map<Language, LessonTranslation> translations;
    private final Instant createdAt;
    private Instant updatedAt;

    public Lesson(UUID id, UUID moduleId, int sortOrder, int durationMinutes,
                  PublicationStatus status, Map<Language, LessonTranslation> translations,
                  Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.moduleId = moduleId;
        this.sortOrder = sortOrder;
        this.durationMinutes = durationMinutes;
        this.status = status;
        this.translations = translations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateSortOrder(int sortOrder) {
        if (sortOrder < 1) {
            throw new IllegalArgumentException("sortOrder must be >= 1");
        }
        this.sortOrder = sortOrder;
        this.updatedAt = Instant.now();
    }

    public void updateDuration(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        this.updatedAt = Instant.now();
    }

    public void publish() {
        if (this.status == PublicationStatus.ARCHIVED) {
            throw new IllegalStateException("An ARCHIVED lesson cannot be published directly. Un-archive first.");
        }
        if (this.status == PublicationStatus.PUBLISHED) {
            throw new IllegalStateException("This lesson is already published.");
        }
        this.status = PublicationStatus.PUBLISHED;
        this.updatedAt = Instant.now();
    }

    public void archive() {
        if (this.status == PublicationStatus.ARCHIVED) {
            throw new IllegalStateException("This lesson is already archived.");
        }
        this.status = PublicationStatus.ARCHIVED;
        this.updatedAt = Instant.now();
    }

    public void unarchive() {
        if (this.status != PublicationStatus.ARCHIVED) {
            throw new IllegalStateException("Only ARCHIVED lessons can be un-archived.");
        }
        this.status = PublicationStatus.DRAFT;
        this.updatedAt = Instant.now();
    }

    public boolean isDeletable() {
        return this.status == PublicationStatus.DRAFT || this.status == PublicationStatus.ARCHIVED;
    }

    public UUID getId() { return id; }
    public UUID getModuleId() { return moduleId; }
    public int getSortOrder() { return sortOrder; }
    public int getDurationMinutes() { return durationMinutes; }
    public PublicationStatus getStatus() { return status; }
    public Map<Language, LessonTranslation> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public String getTitle(Language language) {
        LessonTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.title() : "";
    }

    public String getDescription(Language language) {
        LessonTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.description() : "";
    }

    public record LessonTranslation(String title, String description) {}
}
