package com.hguitar.learning.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.learning.domain.model.Lesson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LessonDomainTest {

    private Lesson lesson(PublicationStatus status) {
        return new Lesson(UUID.randomUUID(), UUID.randomUUID(), 1, 10,
                status, Map.of(), Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("publish — DRAFT → PUBLISHED")
    void publish_fromDraft() {
        Lesson lesson = lesson(PublicationStatus.DRAFT);
        lesson.publish();
        assertThat(lesson.getStatus()).isEqualTo(PublicationStatus.PUBLISHED);
    }

    @Test
    @DisplayName("publish — throws when PUBLISHED")
    void publish_fromPublished_throws() {
        Lesson lesson = lesson(PublicationStatus.PUBLISHED);
        assertThatThrownBy(lesson::publish).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("publish — throws when ARCHIVED")
    void publish_fromArchived_throws() {
        Lesson lesson = lesson(PublicationStatus.ARCHIVED);
        assertThatThrownBy(lesson::publish).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("archive — PUBLISHED → ARCHIVED")
    void archive_fromPublished() {
        Lesson lesson = lesson(PublicationStatus.PUBLISHED);
        lesson.archive();
        assertThat(lesson.getStatus()).isEqualTo(PublicationStatus.ARCHIVED);
    }

    @Test
    @DisplayName("archive — throws when already ARCHIVED")
    void archive_alreadyArchived_throws() {
        Lesson lesson = lesson(PublicationStatus.ARCHIVED);
        assertThatThrownBy(lesson::archive).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("unarchive — ARCHIVED → DRAFT")
    void unarchive_fromArchived() {
        Lesson lesson = lesson(PublicationStatus.ARCHIVED);
        lesson.unarchive();
        assertThat(lesson.getStatus()).isEqualTo(PublicationStatus.DRAFT);
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
        Lesson lesson = lesson(PublicationStatus.DRAFT);
        assertThatThrownBy(() -> lesson.updateSortOrder(0)).isInstanceOf(IllegalArgumentException.class);
    }
}
