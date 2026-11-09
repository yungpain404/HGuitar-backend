package com.hguitar.music.domain.model;

import java.util.UUID;

/**
 * Một bước trong hướng dẫn đánh chi tiết (step-by-step) của 1 công thức strumming.
 * Nội dung được lưu tách theo ngôn ngữ (vi / en) để phục vụ zwe ngôn ngữ.
 */
public class StrummingInstruction {

    private final UUID id;
    private final UUID patternId;
    private final int stepNumber;
    private final String titleVi;
    private final String titleEn;
    private final String contentVi;
    private final String contentEn;
    private final String tipVi;
    private final String tipEn;

    public StrummingInstruction(UUID id, UUID patternId, int stepNumber,
                                String titleVi, String titleEn,
                                String contentVi, String contentEn,
                                String tipVi, String tipEn) {
        this.id = id;
        this.patternId = patternId;
        this.stepNumber = stepNumber;
        this.titleVi = titleVi;
        this.titleEn = titleEn;
        this.contentVi = contentVi;
        this.contentEn = contentEn;
        this.tipVi = tipVi;
        this.tipEn = tipEn;
    }

    public UUID getId() { return id; }
    public UUID getPatternId() { return patternId; }
    public int getStepNumber() { return stepNumber; }
    public String getTitleVi() { return titleVi; }
    public String getTitleEn() { return titleEn; }
    public String getContentVi() { return contentVi; }
    public String getContentEn() { return contentEn; }
    public String getTipVi() { return tipVi; }
    public String getTipEn() { return tipEn; }

    public String getTitle(String lang) {
        return "vi".equalsIgnoreCase(lang) ? titleVi : titleEn;
    }

    public String getContent(String lang) {
        return "vi".equalsIgnoreCase(lang) ? contentVi : contentEn;
    }

    public String getTip(String lang) {
        return "vi".equalsIgnoreCase(lang) ? tipVi : tipEn;
    }
}
