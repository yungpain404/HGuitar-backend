package com.hguitar.music.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "strumming_pattern_instructions", schema = "music")
public class StrummingInstructionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "pattern_id", nullable = false)
    private UUID patternId;

    @Column(name = "step_number", nullable = false)
    private int stepNumber;

    @Column(name = "title_vi", length = 200)
    private String titleVi;

    @Column(name = "title_en", length = 200)
    private String titleEn;

    @Column(name = "content_vi", length = 1000)
    private String contentVi;

    @Column(name = "content_en", length = 1000)
    private String contentEn;

    @Column(name = "tip_vi", length = 500)
    private String tipVi;

    @Column(name = "tip_en", length = 500)
    private String tipEn;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected StrummingInstructionEntity() {}

    public StrummingInstructionEntity(UUID id, UUID patternId, int stepNumber,
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
    public void setPatternId(UUID patternId) { this.patternId = patternId; }
    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
    public String getTitleVi() { return titleVi; }
    public void setTitleVi(String titleVi) { this.titleVi = titleVi; }
    public String getTitleEn() { return titleEn; }
    public void setTitleEn(String titleEn) { this.titleEn = titleEn; }
    public String getContentVi() { return contentVi; }
    public void setContentVi(String contentVi) { this.contentVi = contentVi; }
    public String getContentEn() { return contentEn; }
    public void setContentEn(String contentEn) { this.contentEn = contentEn; }
    public String getTipVi() { return tipVi; }
    public void setTipVi(String tipVi) { this.tipVi = tipVi; }
    public String getTipEn() { return tipEn; }
    public void setTipEn(String tipEn) { this.tipEn = tipEn; }
    public Instant getCreatedAt() { return createdAt; }
}
