package com.hguitar.progress.application.dto.response;

import java.util.List;

public record UserProgressResponse(
        int totalLessonsCompleted,
        List<LessonProgressResponse> lessons
) {}
