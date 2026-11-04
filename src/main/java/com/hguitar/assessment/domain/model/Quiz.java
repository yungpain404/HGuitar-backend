package com.hguitar.assessment.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Quiz {

    private final UUID id;
    private final UUID lessonId;
    private final String titleEn;
    private final String titleVi;
    private final List<Question> questions;
    private final Instant createdAt;

    public Quiz(UUID id, UUID lessonId, String titleEn, String titleVi,
                List<Question> questions, Instant createdAt) {
        this.id = id;
        this.lessonId = lessonId;
        this.titleEn = titleEn;
        this.titleVi = titleVi;
        this.questions = questions;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getLessonId() { return lessonId; }
    public String getTitleEn() { return titleEn; }
    public String getTitleVi() { return titleVi; }
    public List<Question> getQuestions() { return questions; }
    public Instant getCreatedAt() { return createdAt; }
}
