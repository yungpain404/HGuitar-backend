-- V4: Content schema — lesson blocks and translations

CREATE TABLE content.lesson_blocks (
    id            UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_lesson_blocks PRIMARY KEY,
    lesson_id     UNIQUEIDENTIFIER NOT NULL,
    block_type    NVARCHAR(30)     NOT NULL
                      CONSTRAINT ck_lesson_blocks_type CHECK (
                          block_type IN ('TEXT','IMAGE','AUDIO','VIDEO','CHORD_DIAGRAM','PRACTICE_INSTRUCTION')),
    sort_order    INT              NOT NULL,
    media_asset_id UNIQUEIDENTIFIER,               -- nullable; references future media_assets table
    metadata_json NVARCHAR(4000),                  -- optional structured data (e.g. chord positions)
    created_at    DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at    DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_lesson_blocks_lesson_sort_order UNIQUE (lesson_id, sort_order),
    CONSTRAINT ck_lesson_blocks_sort_order CHECK (sort_order >= 1),
    CONSTRAINT fk_lesson_blocks_lesson FOREIGN KEY (lesson_id) REFERENCES learning.lessons(id) ON DELETE CASCADE
);

CREATE TABLE content.lesson_block_translations (
    id         UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_lesson_block_translations PRIMARY KEY,
    block_id   UNIQUEIDENTIFIER NOT NULL,
    language   NVARCHAR(5)      NOT NULL,
    content    NVARCHAR(MAX)    NOT NULL,
    CONSTRAINT uq_lesson_block_translations_block_lang UNIQUE (block_id, language),
    CONSTRAINT fk_lesson_block_translations_block FOREIGN KEY (block_id) REFERENCES content.lesson_blocks(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX ix_lesson_blocks_lesson_id ON content.lesson_blocks(lesson_id);
CREATE INDEX ix_lesson_block_translations_block_id ON content.lesson_block_translations(block_id);
