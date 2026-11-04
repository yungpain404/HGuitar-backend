package com.hguitar.content.infrastructure.persistence.entity;

import com.hguitar.content.domain.model.BlockType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lesson_blocks", schema = "content")
public class LessonBlockEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "lesson_id", nullable = false)
    private UUID lessonId;

    @Column(name = "block_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private BlockType blockType;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "media_asset_id")
    private UUID mediaAssetId;

    @Column(name = "metadata_json", length = 4000)
    private String metadataJson;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<LessonBlockTranslationEntity> translations = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected LessonBlockEntity() {}

    public LessonBlockEntity(UUID id, UUID lessonId, BlockType blockType, int sortOrder,
                             UUID mediaAssetId, String metadataJson) {
        this.id = id;
        this.lessonId = lessonId;
        this.blockType = blockType;
        this.sortOrder = sortOrder;
        this.mediaAssetId = mediaAssetId;
        this.metadataJson = metadataJson;
    }

    public UUID getId() { return id; }
    public UUID getLessonId() { return lessonId; }
    public BlockType getBlockType() { return blockType; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public UUID getMediaAssetId() { return mediaAssetId; }
    public void setMediaAssetId(UUID mediaAssetId) { this.mediaAssetId = mediaAssetId; }
    public String getMetadataJson() { return metadataJson; }
    public void setMetadataJson(String metadataJson) { this.metadataJson = metadataJson; }
    public List<LessonBlockTranslationEntity> getTranslations() { return translations; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
