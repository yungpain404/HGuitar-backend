-- V20: Strumming pattern step-by-step instructions (hướng dẫn đánh chi tiết)
-- Schema: music
-- Lưu ý: không dùng JSONB (SQL Server không hỗ trợ), dùng cột riêng per-language.

CREATE TABLE music.strumming_pattern_instructions (
    id              UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_strumming_pattern_instructions PRIMARY KEY,
    pattern_id      UNIQUEIDENTIFIER NOT NULL,
    step_number     INT              NOT NULL CONSTRAINT ck_strumming_instructions_step CHECK (step_number > 0),
    title_vi        NVARCHAR(200),
    title_en        NVARCHAR(200),
    content_vi      NVARCHAR(1000),
    content_en      NVARCHAR(1000),
    tip_vi          NVARCHAR(500),
    tip_en          NVARCHAR(500),
    created_at      DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_strumming_instructions_pattern FOREIGN KEY (pattern_id) REFERENCES music.strumming_patterns(id) ON DELETE CASCADE,
    CONSTRAINT uq_strumming_instructions_pattern_step UNIQUE (pattern_id, step_number)
);

CREATE INDEX ix_strumming_instructions_pattern ON music.strumming_pattern_instructions(pattern_id);
CREATE INDEX ix_strumming_instructions_pattern_step ON music.strumming_pattern_instructions(pattern_id, step_number);
