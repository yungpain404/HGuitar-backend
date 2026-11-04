package com.hguitar.assessment.application.dto.response;

import java.util.List;
import java.util.UUID;

public record QuizResponse(
        UUID id,
        UUID lessonId,
        String title,
        List<QuestionResponse> questions
) {}
