package com.hguitar.assessment.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "quiz_attempt_answers", schema = "assessment")
public class QuizAttemptAnswerEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttemptEntity attempt;

    @Column(name = "question_id", nullable = false)
    private UUID questionId;

    @Column(name = "selected_answer_id", nullable = false)
    private UUID selectedAnswerId;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    protected QuizAttemptAnswerEntity() {}

    public QuizAttemptAnswerEntity(UUID id, QuizAttemptEntity attempt, UUID questionId,
                                   UUID selectedAnswerId, boolean correct) {
        this.id = id;
        this.attempt = attempt;
        this.questionId = questionId;
        this.selectedAnswerId = selectedAnswerId;
        this.correct = correct;
    }

    public UUID getId() { return id; }
    public QuizAttemptEntity getAttempt() { return attempt; }
    public UUID getQuestionId() { return questionId; }
    public UUID getSelectedAnswerId() { return selectedAnswerId; }
    public boolean isCorrect() { return correct; }
}
