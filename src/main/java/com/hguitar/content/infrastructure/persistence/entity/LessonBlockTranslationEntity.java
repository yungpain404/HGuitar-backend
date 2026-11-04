package com.hguitar.content.infrastructure.persistence.entity;

import com.hguitar.common.domain.Language;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "lesson_block_translations", schema = "content")
public class LessonBlockTranslationEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private LessonBlockEntity block;

    @Column(name = "language", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content;

    protected LessonBlockTranslationEntity() {}

    public LessonBlockTranslationEntity(UUID id, LessonBlockEntity block, Language language, String content) {
        this.id = id;
        this.block = block;
        this.language = language;
        this.content = content;
    }

    public UUID getId() { return id; }
    public LessonBlockEntity getBlock() { return block; }
    public Language getLanguage() { return language; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
