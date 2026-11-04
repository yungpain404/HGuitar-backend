package com.hguitar.learning.domain.model;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.onboarding.domain.model.GuitarType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Course {

    private final UUID id;
    private final GuitarType guitarType;
    private int sortOrder;
    private PublicationStatus status;
    private final Map<Language, CourseTranslation> translations;
    private final Instant createdAt;
    private Instant updatedAt;

    public Course(UUID id, GuitarType guitarType, int sortOrder, PublicationStatus status,
                  Map<Language, CourseTranslation> translations, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.guitarType = guitarType;
        this.sortOrder = sortOrder;
        this.status = status;
        this.translations = translations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void publish() {
        this.status = PublicationStatus.PUBLISHED;
        this.updatedAt = Instant.now();
    }

    public void archive() {
        this.status = PublicationStatus.ARCHIVED;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public GuitarType getGuitarType() { return guitarType; }
    public int getSortOrder() { return sortOrder; }
    public PublicationStatus getStatus() { return status; }
    public Map<Language, CourseTranslation> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public String getTitle(Language language) {
        CourseTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.title() : "";
    }

    public String getDescription(Language language) {
        CourseTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.description() : "";
    }

    public record CourseTranslation(String title, String description) {}
}
