package com.hguitar.assessment.application.dto.response;

import com.hguitar.assessment.domain.model.QuizAttempt;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AttemptResponse(
        UUID id,
        UUID quizId,
        int attemptNumber,
        QuizAttempt.Status status,
        Instant startedAt,
        Instant submittedAt,
        BigDecimal score,
        List<AttemptAnswerResponse> answers
) {}
