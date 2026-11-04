package com.hguitar.content.domain.model;

import com.hguitar.common.domain.Language;

import java.util.UUID;

/**
 * Localized text content for a LessonBlock.
 * Used by TEXT and PRACTICE_INSTRUCTION blocks primarily.
 * For media blocks the text might be a caption or alt text.
 */
public class LessonBlockTranslation {

    private final UUID id;
    private final UUID blockId;
    private final Language language;
    private String content; // rich text or plain text depending on block type

    public LessonBlockTranslation(UUID id, UUID blockId, Language language, String content) {
        this.id = id;
        this.blockId = blockId;
        this.language = language;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public UUID getId() { return id; }
    public UUID getBlockId() { return blockId; }
    public Language getLanguage() { return language; }
    public String getContent() { return content; }
}
