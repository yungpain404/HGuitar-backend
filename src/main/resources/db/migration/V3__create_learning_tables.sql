-- V3: Learning schema — courses, modules, lessons, translations

-- ============================================================
-- Courses
-- ============================================================
CREATE TABLE learning.courses (
    id             UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_courses PRIMARY KEY,
    guitar_type    NVARCHAR(20)     NOT NULL,  -- ACOUSTIC | CLASSICAL
    sort_order     INT              NOT NULL DEFAULT 1,
    status         NVARCHAR(20)     NOT NULL DEFAULT 'DRAFT'
                       CONSTRAINT ck_courses_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    created_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT ck_courses_guitar_type CHECK (guitar_type IN ('ACOUSTIC','CLASSICAL'))
);

CREATE TABLE learning.course_translations (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_course_translations PRIMARY KEY,
    course_id   UNIQUEIDENTIFIER NOT NULL,
    language    NVARCHAR(5)      NOT NULL,
    title       NVARCHAR(500)    NOT NULL,
    description NVARCHAR(2000),
    CONSTRAINT uq_course_translations_course_lang UNIQUE (course_id, language),
    CONSTRAINT fk_course_translations_course FOREIGN KEY (course_id) REFERENCES learning.courses(id) ON DELETE CASCADE
);

-- ============================================================
-- Modules
-- ============================================================
CREATE TABLE learning.modules (
    id         UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_modules PRIMARY KEY,
    course_id  UNIQUEIDENTIFIER NOT NULL,
    sort_order INT              NOT NULL DEFAULT 1,
    status     NVARCHAR(20)     NOT NULL DEFAULT 'DRAFT'
                   CONSTRAINT ck_modules_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    created_at DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_modules_course FOREIGN KEY (course_id) REFERENCES learning.courses(id) ON DELETE CASCADE
);

CREATE TABLE learning.module_translations (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_module_translations PRIMARY KEY,
    module_id   UNIQUEIDENTIFIER NOT NULL,
    language    NVARCHAR(5)      NOT NULL,
    title       NVARCHAR(500)    NOT NULL,
    description NVARCHAR(2000),
    CONSTRAINT uq_module_translations_module_lang UNIQUE (module_id, language),
    CONSTRAINT fk_module_translations_module FOREIGN KEY (module_id) REFERENCES learning.modules(id) ON DELETE CASCADE
);

-- ============================================================
-- Lessons
-- ============================================================
CREATE TABLE learning.lessons (
    id               UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_lessons PRIMARY KEY,
    module_id        UNIQUEIDENTIFIER NOT NULL,
    sort_order       INT              NOT NULL,
    duration_minutes INT              NOT NULL DEFAULT 0,
    status           NVARCHAR(20)     NOT NULL DEFAULT 'DRAFT'
                         CONSTRAINT ck_lessons_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    created_at       DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at       DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_lessons_module_sort_order UNIQUE (module_id, sort_order),
    CONSTRAINT ck_lessons_sort_order CHECK (sort_order >= 1),
    CONSTRAINT ck_lessons_duration CHECK (duration_minutes >= 0),
    CONSTRAINT fk_lessons_module FOREIGN KEY (module_id) REFERENCES learning.modules(id)
);

CREATE TABLE learning.lesson_translations (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_lesson_translations PRIMARY KEY,
    lesson_id   UNIQUEIDENTIFIER NOT NULL,
    language    NVARCHAR(5)      NOT NULL,
    title       NVARCHAR(500)    NOT NULL,
    description NVARCHAR(2000),
    CONSTRAINT uq_lesson_translations_lesson_lang UNIQUE (lesson_id, language),
    CONSTRAINT fk_lesson_translations_lesson FOREIGN KEY (lesson_id) REFERENCES learning.lessons(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX ix_lessons_module_id ON learning.lessons(module_id);
CREATE INDEX ix_lessons_status ON learning.lessons(status);
CREATE INDEX ix_lesson_translations_lesson_id ON learning.lesson_translations(lesson_id);
