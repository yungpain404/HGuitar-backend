package com.hguitar.assessment.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class QuizAttempt {

    public enum Status { IN_PROGRESS, SUBMITTED }

    private final UUID id;
    private final UUID quizId;
    private final UUID userId;
    private final int attemptNumber;
    private final Instant startedAt;
    private Instant submittedAt;
    private BigDecimal score;
    private Status status;
    private List<AttemptAnswer> answers;

    public QuizAttempt(UUID id, UUID quizId, UUID userId, int attemptNumber,
                       Instant startedAt, Instant submittedAt, BigDecimal score,
                       Status status, List<AttemptAnswer> answers) {
        this.id = id;
        this.quizId = quizId;
        this.userId = userId;
        this.attemptNumber = attemptNumber;
        this.startedAt = startedAt;
        this.submittedAt = submittedAt;
        this.score = score;
        this.status = status;
        this.answers = answers;
    }

    public void submit(List<AttemptAnswer> answers, BigDecimal score) {
        this.answers = answers;
        this.score = score;
        this.submittedAt = Instant.now();
        this.status = Status.SUBMITTED;
    }

    public UUID getId() { return id; }
    public UUID getQuizId() { return quizId; }
    public UUID getUserId() { return userId; }
    public int getAttemptNumber() { return attemptNumber; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getSubmittedAt() { return submittedAt; }
    public BigDecimal getScore() { return score; }
    public Status getStatus() { return status; }
    public List<AttemptAnswer> getAnswers() { return answers; }
}
