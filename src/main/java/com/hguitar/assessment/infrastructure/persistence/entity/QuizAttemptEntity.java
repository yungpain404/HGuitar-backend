package com.hguitar.assessment.infrastructure.persistence.entity;

import com.hguitar.assessment.domain.model.QuizAttempt;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quiz_attempts", schema = "assessment")
public class QuizAttemptEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "quiz_id", nullable = false)
    private UUID quizId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private QuizAttempt.Status status;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<QuizAttemptAnswerEntity> answers = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected QuizAttemptEntity() {}

    public QuizAttemptEntity(UUID id, UUID quizId, UUID userId, int attemptNumber,
                             Instant startedAt, QuizAttempt.Status status) {
        this.id = id;
        this.quizId = quizId;
        this.userId = userId;
        this.attemptNumber = attemptNumber;
        this.startedAt = startedAt;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getQuizId() { return quizId; }
    public UUID getUserId() { return userId; }
    public int getAttemptNumber() { return attemptNumber; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public QuizAttempt.Status getStatus() { return status; }
    public void setStatus(QuizAttempt.Status status) { this.status = status; }
    public List<QuizAttemptAnswerEntity> getAnswers() { return answers; }
    public Instant getCreatedAt() { return createdAt; }
}
