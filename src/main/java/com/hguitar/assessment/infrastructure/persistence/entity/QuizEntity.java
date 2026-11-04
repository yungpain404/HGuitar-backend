package com.hguitar.assessment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quizzes", schema = "assessment")
public class QuizEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "lesson_id", nullable = false)
    private UUID lessonId;

    @Column(name = "title_en", nullable = false, length = 500)
    private String titleEn;

    @Column(name = "title_vi", length = 500)
    private String titleVi;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<QuestionEntity> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected QuizEntity() {}

    public QuizEntity(UUID id, UUID lessonId, String titleEn, String titleVi) {
        this.id = id;
        this.lessonId = lessonId;
        this.titleEn = titleEn;
        this.titleVi = titleVi;
    }

    public UUID getId() { return id; }
    public UUID getLessonId() { return lessonId; }
    public String getTitleEn() { return titleEn; }
    public String getTitleVi() { return titleVi; }
    public List<QuestionEntity> getQuestions() { return questions; }
    public Instant getCreatedAt() { return createdAt; }
}
