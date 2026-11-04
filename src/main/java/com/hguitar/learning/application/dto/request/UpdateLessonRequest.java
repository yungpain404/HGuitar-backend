package com.hguitar.learning.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateLessonRequest(

    @Min(value = 1, message = "sortOrder must be >= 1")
    int sortOrder,

    @Min(value = 0, message = "durationMinutes must be >= 0")
    int durationMinutes,

    @NotBlank(message = "titleVi is required")
    @Size(max = 500, message = "titleVi must not exceed 500 characters")
    String titleVi,

    @Size(max = 2000, message = "descriptionVi must not exceed 2000 characters")
    String descriptionVi,

    @NotBlank(message = "titleEn is required")
    @Size(max = 500, message = "titleEn must not exceed 500 characters")
    String titleEn,

    @Size(max = 2000, message = "descriptionEn must not exceed 2000 characters")
    String descriptionEn
) {}
