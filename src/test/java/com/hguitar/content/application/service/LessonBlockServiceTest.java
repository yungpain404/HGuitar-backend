package com.hguitar.content.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.content.application.dto.request.CreateLessonBlockRequest;
import com.hguitar.content.domain.model.BlockType;
import com.hguitar.content.domain.model.LessonBlock;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import com.hguitar.content.domain.service.LessonStatusChecker;
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
class LessonBlockServiceTest {

    @Mock LessonBlockRepository blockRepository;
    @Mock LessonStatusChecker lessonStatusChecker;

    @InjectMocks LessonBlockService service;

    private UUID lessonId;
    private UUID blockId;

    @BeforeEach
    void setUp() {
        lessonId = UUID.randomUUID();
        blockId = UUID.randomUUID();
    }

    private LessonBlock blockFor(UUID ownerLessonId) {
        return new LessonBlock(blockId, ownerLessonId, BlockType.TEXT, 1,
                null, null, Map.of(), Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("createBlock — happy path saves the block")
    void createBlock_happyPath() {
        when(lessonStatusChecker.getLessonStatus(lessonId))
                .thenReturn(Optional.of(PublicationStatus.PUBLISHED));
        when(blockRepository.existsByLessonIdAndSortOrderAndIdNot(eq(lessonId), eq(1), any())).thenReturn(false);
        when(blockRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CreateLessonBlockRequest request = new CreateLessonBlockRequest(
                BlockType.TEXT, 1, null, null, "Nội dung", "Content");

        var response = service.createBlock(lessonId, request);

        assertThat(response).isNotNull();
        assertThat(response.blockType()).isEqualTo(BlockType.TEXT);
        assertThat(response.content()).isEqualTo("Content");
        verify(blockRepository).save(any());
    }

    @Test
    @DisplayName("createBlock — throws for ARCHIVED lesson")
    void createBlock_archivedLesson_throws() {
        when(lessonStatusChecker.getLessonStatus(lessonId))
                .thenReturn(Optional.of(PublicationStatus.ARCHIVED));

        CreateLessonBlockRequest request = new CreateLessonBlockRequest(
                BlockType.TEXT, 1, null, null, "x", "x");

        assertThatThrownBy(() -> service.createBlock(lessonId, request))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("createBlock — missing lesson throws ResourceNotFoundException")
    void createBlock_missingLesson_throws() {
        when(lessonStatusChecker.getLessonStatus(lessonId)).thenReturn(Optional.empty());

        CreateLessonBlockRequest request = new CreateLessonBlockRequest(
                BlockType.TEXT, 1, null, null, "x", "x");

        assertThatThrownBy(() -> service.createBlock(lessonId, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("createBlock — throws ConflictException on duplicate sortOrder")
    void createBlock_duplicateSortOrder() {
        when(lessonStatusChecker.getLessonStatus(lessonId))
                .thenReturn(Optional.of(PublicationStatus.DRAFT));
        when(blockRepository.existsByLessonIdAndSortOrderAndIdNot(eq(lessonId), eq(1), any())).thenReturn(true);

        CreateLessonBlockRequest request = new CreateLessonBlockRequest(
                BlockType.TEXT, 1, null, null, "x", "x");

        assertThatThrownBy(() -> service.createBlock(lessonId, request))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    @DisplayName("deleteBlock — removes the block")
    void deleteBlock_happyPath() {
        when(blockRepository.findById(blockId)).thenReturn(Optional.of(blockFor(lessonId)));

        service.deleteBlock(lessonId, blockId);

        verify(blockRepository).deleteById(blockId);
    }

    @Test
    @DisplayName("deleteBlock — throws when block belongs to a different lesson")
    void deleteBlock_wrongLesson() {
        when(blockRepository.findById(blockId)).thenReturn(Optional.of(blockFor(UUID.randomUUID())));

        assertThatThrownBy(() -> service.deleteBlock(lessonId, blockId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
