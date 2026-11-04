package com.hguitar.content.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.content.application.dto.request.CreateLessonBlockRequest;
import com.hguitar.content.application.dto.request.UpdateLessonBlockRequest;
import com.hguitar.content.application.dto.response.LessonBlockResponse;
import com.hguitar.content.domain.model.LessonBlock;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import com.hguitar.content.domain.service.LessonStatusChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class LessonBlockService {

    private final LessonBlockRepository blockRepository;
    private final LessonStatusChecker lessonStatusChecker;

    public LessonBlockService(LessonBlockRepository blockRepository,
                               LessonStatusChecker lessonStatusChecker) {
        this.blockRepository = blockRepository;
        this.lessonStatusChecker = lessonStatusChecker;
    }

    public LessonBlockResponse createBlock(UUID lessonId, CreateLessonBlockRequest request) {
        guardLessonEditable(lessonId);

        UUID blockId = UUID.randomUUID();
        guardBlockSortOrderUnique(lessonId, request.sortOrder(), blockId);

        Map<Language, String> translations = buildTranslations(request.contentVi(), request.contentEn());

        Instant now = Instant.now();
        LessonBlock block = new LessonBlock(blockId, lessonId, request.blockType(),
                request.sortOrder(), request.mediaAssetId(), request.metadataJson(),
                translations, now, now);
        block = blockRepository.save(block);
        return toResponse(block, Language.EN);
    }

    @Transactional(readOnly = true)
    public List<LessonBlockResponse> getBlocksForLesson(UUID lessonId, String lang) {
        if (!lessonStatusChecker.lessonExists(lessonId)) {
            throw new ResourceNotFoundException("Lesson", lessonId);
        }
        Language language = Language.fromCode(lang);
        return blockRepository.findByLessonId(lessonId).stream()
                .map(b -> toResponse(b, language))
                .toList();
    }

    @Transactional(readOnly = true)
    public LessonBlockResponse getBlock(UUID lessonId, UUID blockId) {
        LessonBlock block = requireBlock(blockId);
        if (!block.getLessonId().equals(lessonId)) {
            throw new ResourceNotFoundException("LessonBlock", blockId);
        }
        return toResponse(block, Language.EN);
    }

    public LessonBlockResponse updateBlock(UUID lessonId, UUID blockId, UpdateLessonBlockRequest request) {
        guardLessonEditable(lessonId);

        LessonBlock block = requireBlock(blockId);
        if (!block.getLessonId().equals(lessonId)) {
            throw new ResourceNotFoundException("LessonBlock", blockId);
        }

        if (request.sortOrder() != block.getSortOrder()) {
            guardBlockSortOrderUnique(lessonId, request.sortOrder(), blockId);
            block.updateSortOrder(request.sortOrder());
        }
        block.updateMetadata(request.metadataJson());
        block.updateMediaAsset(request.mediaAssetId());

        // Rebuild translations
        Map<Language, String> updatedTranslations = new EnumMap<>(block.getTranslations());
        if (request.contentVi() != null) updatedTranslations.put(Language.VI, request.contentVi());
        if (request.contentEn() != null) updatedTranslations.put(Language.EN, request.contentEn());

        LessonBlock updated = new LessonBlock(block.getId(), block.getLessonId(), block.getBlockType(),
                block.getSortOrder(), block.getMediaAssetId(), block.getMetadataJson(),
                updatedTranslations, block.getCreatedAt(), Instant.now());
        updated = blockRepository.save(updated);
        return toResponse(updated, Language.EN);
    }

    public void deleteBlock(UUID lessonId, UUID blockId) {
        LessonBlock block = requireBlock(blockId);
        if (!block.getLessonId().equals(lessonId)) {
            throw new ResourceNotFoundException("LessonBlock", blockId);
        }
        blockRepository.deleteById(blockId);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void guardLessonEditable(UUID lessonId) {
        PublicationStatus status = lessonStatusChecker.getLessonStatus(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", lessonId));
        if (status == PublicationStatus.ARCHIVED) {
            throw new BusinessRuleException(
                    "Cannot modify content blocks of an ARCHIVED lesson. Un-archive it first.");
        }
    }

    private LessonBlock requireBlock(UUID blockId) {
        return blockRepository.findById(blockId)
                .orElseThrow(() -> new ResourceNotFoundException("LessonBlock", blockId));
    }

    private void guardBlockSortOrderUnique(UUID lessonId, int sortOrder, UUID excludedId) {
        if (blockRepository.existsByLessonIdAndSortOrderAndIdNot(lessonId, sortOrder, excludedId)) {
            throw new ConflictException("sortOrder " + sortOrder + " is already used in this lesson.");
        }
    }

    private Map<Language, String> buildTranslations(String contentVi, String contentEn) {
        Map<Language, String> map = new EnumMap<>(Language.class);
        if (contentVi != null) map.put(Language.VI, contentVi);
        if (contentEn != null) map.put(Language.EN, contentEn);
        return map;
    }

    private LessonBlockResponse toResponse(LessonBlock block, Language language) {
        return new LessonBlockResponse(block.getId(), block.getLessonId(), block.getBlockType(),
                block.getSortOrder(), block.getMediaAssetId(), block.getMetadataJson(),
                block.getContent(language));
    }
}
