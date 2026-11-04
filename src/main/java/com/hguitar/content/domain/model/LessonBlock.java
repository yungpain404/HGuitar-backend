package com.hguitar.content.domain.model;

import com.hguitar.common.domain.Language;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class LessonBlock {

    private final UUID id;
    private final UUID lessonId;
    private final BlockType blockType;
    private int sortOrder;
    private UUID mediaAssetId;
    private String metadataJson;
    private final Map<Language, String> translations;
    private final Instant createdAt;
    private Instant updatedAt;

    public LessonBlock(UUID id, UUID lessonId, BlockType blockType, int sortOrder,
                       UUID mediaAssetId, String metadataJson,
                       Map<Language, String> translations,
                       Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.lessonId = lessonId;
        this.blockType = blockType;
        this.sortOrder = sortOrder;
        this.mediaAssetId = mediaAssetId;
        this.metadataJson = metadataJson;
        this.translations = translations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public UUID getLessonId() { return lessonId; }
    public BlockType getBlockType() { return blockType; }
    public int getSortOrder() { return sortOrder; }
    public UUID getMediaAssetId() { return mediaAssetId; }
    public String getMetadataJson() { return metadataJson; }
    public Map<Language, String> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void updateSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        this.updatedAt = Instant.now();
    }

    public void updateMetadata(String metadataJson) {
        this.metadataJson = metadataJson;
        this.updatedAt = Instant.now();
    }

    public void updateMediaAsset(UUID mediaAssetId) {
        this.mediaAssetId = mediaAssetId;
        this.updatedAt = Instant.now();
    }

    public String getContent(Language language) {
        String content = translations.get(language);
        if (content == null) content = translations.get(Language.EN);
        return content != null ? content : "";
    }
}
