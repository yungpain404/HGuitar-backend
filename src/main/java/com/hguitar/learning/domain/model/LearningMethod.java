package com.hguitar.learning.domain.model;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A common learning method (phương pháp học phổ biến) for beginners,
 * e.g. "4 hợp âm vàng", "công thức đánh", "học theo bài hát".
 */
public class LearningMethod {

    private final UUID id;
    private final String nameKey;
    private final String difficulty;
    private final String targetGuitar;
    private final int estimatedWeeks;
    private final int stepsCount;
    private final PublicationStatus status;
    private final int sortOrder;
    private final Map<Language, LearningMethodTranslation> translations;
    private final Instant createdAt;
    private Instant updatedAt;

    public LearningMethod(UUID id, String nameKey, String difficulty, String targetGuitar,
                          int estimatedWeeks, int stepsCount, PublicationStatus status, int sortOrder,
                          Map<Language, LearningMethodTranslation> translations, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.nameKey = nameKey;
        this.difficulty = difficulty;
        this.targetGuitar = targetGuitar;
        this.estimatedWeeks = estimatedWeeks;
        this.stepsCount = stepsCount;
        this.status = status;
        this.sortOrder = sortOrder;
        this.translations = translations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getNameKey() { return nameKey; }
    public String getDifficulty() { return difficulty; }
    public String getTargetGuitar() { return targetGuitar; }
    public int getEstimatedWeeks() { return estimatedWeeks; }
    public int getStepsCount() { return stepsCount; }
    public PublicationStatus getStatus() { return status; }
    public int getSortOrder() { return sortOrder; }
    public Map<Language, LearningMethodTranslation> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public String getTitle(Language language) {
        LearningMethodTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.title() : "";
    }

    public String getDescription(Language language) {
        LearningMethodTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.description() : "";
    }

    public List<String> getSteps(Language language) {
        LearningMethodTranslation t = translations.get(language);
        if (t == null) t = translations.get(Language.EN);
        return t != null ? t.steps() : List.of();
    }

    public record LearningMethodTranslation(String title, String description, List<String> steps) {}
}
