package com.hguitar.learning.domain.model;

import com.hguitar.common.domain.Language;

import java.util.UUID;

/**
 * Localized metadata for a Lesson.
 * Carries the lesson title and description in one language.
 */
public class LessonTranslation {

    private final UUID id;
    private final UUID lessonId;
    private final Language language;
    private String title;
    private String description;

    public LessonTranslation(UUID id, UUID lessonId, Language language,
                             String title, String description) {
        this.id = id;
        this.lessonId = lessonId;
        this.language = language;
        this.title = title;
        this.description = description;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public UUID getId() { return id; }
    public UUID getLessonId() { return lessonId; }
    public Language getLanguage() { return language; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
