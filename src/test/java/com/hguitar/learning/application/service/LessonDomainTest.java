package com.hguitar.learning.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.domain.model.Lesson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class LessonDomainTest {

    private Lesson lesson(PublicationStatus status) {
        return new Lesson(UUID.randomUUID(), UUID.randomUUID(), 1, 10,
                status, Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("publish — DRAFT → PUBLISHED")
    void publish_fromDraft() {
        Lesson l = lesson(PublicationStatus.DRAFT);
        l.publish();
        assertThat(l.getStatus()).isEqualTo(PublicationStatus.PUBLISHED);
    }

    @Test
    @DisplayName("publish — throws when PUBLISHED")
    void publish_fromPublished_throws() {
        Lesson l = lesson(PublicationStatus.PUBLISHED);
        assertThatThrownBy(l::publish).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("publish — throws when ARCHIVED")
    void publish_fromArchived_throws() {
        Lesson l = lesson(PublicationStatus.ARCHIVED);
        assertThatThrownBy(l::publish).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("archive — PUBLISHED → ARCHIVED")
    void archive_fromPublished() {
        Lesson l = lesson(PublicationStatus.PUBLISHED);
        l.archive();
        assertThat(l.getStatus()).isEqualTo(PublicationStatus.ARCHIVED);
    }

    @Test
    @DisplayName("archive — throws when already ARCHIVED")
    void archive_alreadyArchived_throws() {
        Lesson l = lesson(PublicationStatus.ARCHIVED);
        assertThatThrownBy(l::archive).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("unarchive — ARCHIVED → DRAFT")
    void unarchive_fromArchived() {
        Lesson l = lesson(PublicationStatus.ARCHIVED);
        l.unarchive();
        assertThat(l.getStatus()).isEqualTo(PublicationStatus.DRAFT);
    }

    @Test
    @DisplayName("isDeletable — true for DRAFT and ARCHIVED, false for PUBLISHED")
    void isDeletable() {
        assertThat(lesson(PublicationStatus.DRAFT).isDeletable()).isTrue();
        assertThat(lesson(PublicationStatus.ARCHIVED).isDeletable()).isTrue();
        assertThat(lesson(PublicationStatus.PUBLISHED).isDeletable()).isFalse();
    }

    @Test
    @DisplayName("updateSortOrder — throws for sortOrder < 1")
    void updateSortOrder_invalid() {
        Lesson l = lesson(PublicationStatus.DRAFT);
        assertThatThrownBy(() -> l.updateSortOrder(0)).isInstanceOf(IllegalArgumentException.class);
    }
}
