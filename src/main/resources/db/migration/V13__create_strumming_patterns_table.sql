-- V13: Strumming patterns (cách đánh) — beginner friendly "formula" patterns
-- Schema: music

CREATE TABLE music.strumming_patterns (
    id              UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_strumming_patterns PRIMARY KEY,
    name            NVARCHAR(100)    NOT NULL,          -- e.g. N'Công thức 1: ↓ ↓↑↑ ↓ ↑'
    formula         NVARCHAR(100)    NOT NULL,          -- e.g. N'↓ ↓↑↑ ↓ ↑'
    description     NVARCHAR(1000),
    difficulty      NVARCHAR(20)     NOT NULL DEFAULT 'BEGINNER'
                        CONSTRAINT ck_strumming_patterns_difficulty CHECK (difficulty IN ('BEGINNER','INTERMEDIATE','ADVANCED')),
    guitar_type     NVARCHAR(20)     NOT NULL
                        CONSTRAINT ck_strumming_patterns_guitar_type CHECK (guitar_type IN ('ACOUSTIC','CLASSICAL','ALL')),
    bpm_min         INT              NULL,
    bpm_max         INT              NULL,
    is_formula      BIT              NOT NULL DEFAULT 1,  -- 1 = công thức đánh dành cho người mới bắt đầu
    status          NVARCHAR(20)     NOT NULL DEFAULT 'PUBLISHED'
                        CONSTRAINT ck_strumming_patterns_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    sort_order      INT              NOT NULL DEFAULT 1,
    created_at      DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at      DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME()
);

CREATE INDEX ix_strumming_patterns_guitar_type ON music.strumming_patterns(guitar_type);
CREATE INDEX ix_strumming_patterns_difficulty ON music.strumming_patterns(difficulty);
CREATE INDEX ix_strumming_patterns_status ON music.strumming_patterns(status);
