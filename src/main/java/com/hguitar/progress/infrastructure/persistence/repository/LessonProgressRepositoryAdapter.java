package com.hguitar.progress.infrastructure.persistence.repository;

import com.hguitar.progress.domain.model.LessonProgress;
import com.hguitar.progress.domain.repository.LessonProgressRepository;
import com.hguitar.progress.infrastructure.persistence.entity.LessonProgressEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LessonProgressRepositoryAdapter implements LessonProgressRepository {

    private final JpaLessonProgressRepository jpa;

    public LessonProgressRepositoryAdapter(JpaLessonProgressRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public LessonProgress save(LessonProgress progress) {
        LessonProgressEntity entity = jpa.findByUserIdAndLessonId(progress.getUserId(), progress.getLessonId())
                .orElse(new LessonProgressEntity(progress.getId(), progress.getUserId(),
                        progress.getLessonId(), progress.getStatus()));

        entity.setStatus(progress.getStatus());
        entity.setCompletedAt(progress.getCompletedAt());

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<LessonProgress> findByUserIdAndLessonId(UUID userId, UUID lessonId) {
        return jpa.findByUserIdAndLessonId(userId, lessonId).map(this::toDomain);
    }

    @Override
    public List<LessonProgress> findByUserId(UUID userId) {
        return jpa.findByUserId(userId).stream().map(this::toDomain).toList();
    }

    private LessonProgress toDomain(LessonProgressEntity e) {
        return new LessonProgress(e.getId(), e.getUserId(), e.getLessonId(),
                e.getStatus(), e.getCompletedAt(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
