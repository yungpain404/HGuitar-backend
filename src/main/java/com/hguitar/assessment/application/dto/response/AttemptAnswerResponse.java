package com.hguitar.assessment.application.dto.response;

import java.util.UUID;

public record AttemptAnswerResponse(
        UUID questionId,
        UUID selectedAnswerId,
        boolean correct
) {}
