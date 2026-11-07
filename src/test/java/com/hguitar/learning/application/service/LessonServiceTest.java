package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.application.dto.request.CreateLessonRequest;
import com.hguitar.learning.application.dto.request.UpdateLessonRequest;
import com.hguitar.learning.application.dto.response.LessonResponse;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.domain.repository.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock LessonRepository lessonRepository;
    @Mock ModuleRepository moduleRepository;

    @InjectMocks LessonService service;

    private UUID moduleId;
    private UUID lessonId;
    private Lesson draftLesson;

    @BeforeEach
    void setUp() {
        moduleId = UUID.randomUUID();
        lessonId = UUID.randomUUID();
        draftLesson = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.DRAFT,
                Map.of(Language.EN, new Lesson.LessonTranslation("Lesson 1", "Description")),
                Instant.now(), Instant.now());
    }

    private Lesson lessonWithStatus(PublicationStatus status) {
        return new Lesson(lessonId, moduleId, 1, 10,
                status,
                Map.of(Language.EN, new Lesson.LessonTranslation("Lesson 1", "Description")),
                Instant.now(), Instant.now());
    }

    // -------------------------------------------------------------------------
    // createLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("createLesson — happy path creates a DRAFT lesson")
    void createLesson_happyPath() {
        when(moduleRepository.existsById(moduleId)).thenReturn(true);
        when(lessonRepository.existsByModuleIdAndSortOrderAndIdNot(eq(moduleId), eq(1), any())).thenReturn(false);
        when(lessonRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CreateLessonRequest request = new CreateLessonRequest(
                moduleId, 1, 10,
                "Bài học 1", "Mô tả",
                "Lesson 1", "Description");

        LessonResponse response = service.createLesson(request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(PublicationStatus.DRAFT);
        assertThat(response.sortOrder()).isEqualTo(1);
        assertThat(response.title()).isEqualTo("Lesson 1");
        verify(lessonRepository).save(any());
    }

    @Test
    @DisplayName("createLesson — throws when module does not exist")
    void createLesson_moduleNotFound() {
        when(moduleRepository.existsById(moduleId)).thenReturn(false);

        CreateLessonRequest request = new CreateLessonRequest(
                moduleId, 1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.createLesson(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("createLesson — throws ConflictException on duplicate sortOrder")
    void createLesson_duplicateSortOrder() {
        when(moduleRepository.existsById(moduleId)).thenReturn(true);
        when(lessonRepository.existsByModuleIdAndSortOrderAndIdNot(eq(moduleId), eq(1), any())).thenReturn(true);

        CreateLessonRequest request = new CreateLessonRequest(
                moduleId, 1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.createLesson(request))
                .isInstanceOf(ConflictException.class);
    }

    // -------------------------------------------------------------------------
    // publishLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("publishLesson — transitions DRAFT → PUBLISHED")
    void publishLesson_fromDraft() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));
        when(lessonRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LessonResponse response = service.publishLesson(lessonId);

        assertThat(response.status()).isEqualTo(PublicationStatus.PUBLISHED);
    }

    @Test
    @DisplayName("publishLesson — throws BusinessRuleException when already PUBLISHED")
    void publishLesson_alreadyPublished() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.PUBLISHED)));

        assertThatThrownBy(() -> service.publishLesson(lessonId))
                .isInstanceOf(BusinessRuleException.class);
    }

    // -------------------------------------------------------------------------
    // archiveLesson / unarchiveLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("archiveLesson — transitions PUBLISHED → ARCHIVED")
    void archiveLesson_fromPublished() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.PUBLISHED)));
        when(lessonRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LessonResponse response = service.archiveLesson(lessonId);

        assertThat(response.status()).isEqualTo(PublicationStatus.ARCHIVED);
    }

    @Test
    @DisplayName("archiveLesson — throws BusinessRuleException when already ARCHIVED")
    void archiveLesson_alreadyArchived() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.ARCHIVED)));

        assertThatThrownBy(() -> service.archiveLesson(lessonId))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("unarchiveLesson — transitions ARCHIVED → DRAFT")
    void unarchiveLesson_fromArchived() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.ARCHIVED)));
        when(lessonRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LessonResponse response = service.unarchiveLesson(lessonId);

        assertThat(response.status()).isEqualTo(PublicationStatus.DRAFT);
    }

    // -------------------------------------------------------------------------
    // deleteLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("deleteLesson — deletes a DRAFT lesson")
    void deleteLesson_draft() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));

        service.deleteLesson(lessonId);

        verify(lessonRepository).deleteById(lessonId);
    }

    @Test
    @DisplayName("deleteLesson — throws BusinessRuleException for PUBLISHED lesson")
    void deleteLesson_published_throws() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.PUBLISHED)));

        assertThatThrownBy(() -> service.deleteLesson(lessonId))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Archive the lesson first");
    }

    // -------------------------------------------------------------------------
    // updateLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateLesson — throws BusinessRuleException for ARCHIVED lesson")
    void updateLesson_archived_throws() {
        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lessonWithStatus(PublicationStatus.ARCHIVED)));

        UpdateLessonRequest request = new UpdateLessonRequest(1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.updateLesson(lessonId, request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("ARCHIVED");
    }
}
