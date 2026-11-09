-- V15: Common learning methods (phương pháp học phổ biến) for beginners
-- Schema: learning

CREATE TABLE learning.learning_methods (
    id             UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_learning_methods PRIMARY KEY,
    name_key       NVARCHAR(100)    NOT NULL,          -- e.g. 'method_golden_4_chords'
    difficulty     NVARCHAR(20)     NOT NULL DEFAULT 'BEGINNER'
                        CONSTRAINT ck_learning_methods_difficulty CHECK (difficulty IN ('BEGINNER','INTERMEDIATE','ADVANCED')),
    target_guitar  NVARCHAR(20)     NOT NULL DEFAULT 'ACOUSTIC'
                        CONSTRAINT ck_learning_methods_guitar CHECK (target_guitar IN ('ACOUSTIC','CLASSICAL','ALL')),
    estimated_weeks INT             NOT NULL DEFAULT 4 CONSTRAINT ck_learning_methods_weeks CHECK (estimated_weeks > 0),
    steps_count    INT              NOT NULL DEFAULT 1 CONSTRAINT ck_learning_methods_steps CHECK (steps_count > 0),
    status         NVARCHAR(20)     NOT NULL DEFAULT 'PUBLISHED'
                        CONSTRAINT ck_learning_methods_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    sort_order     INT              NOT NULL DEFAULT 1,
    created_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME()
);

CREATE TABLE learning.learning_method_translations (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_learning_method_translations PRIMARY KEY,
    method_id   UNIQUEIDENTIFIER NOT NULL,
    language    NVARCHAR(5)      NOT NULL,
    title       NVARCHAR(500)    NOT NULL,
    description NVARCHAR(2000),
    steps_json  NVARCHAR(MAX)    NOT NULL,              -- JSON array of step descriptions
    CONSTRAINT uq_learning_method_translations_method_lang UNIQUE (method_id, language),
    CONSTRAINT fk_learning_method_translations_method FOREIGN KEY (method_id) REFERENCES learning.learning_methods(id) ON DELETE CASCADE
);

CREATE INDEX ix_learning_methods_difficulty ON learning.learning_methods(difficulty);
CREATE INDEX ix_learning_methods_status ON learning.learning_methods(status);
