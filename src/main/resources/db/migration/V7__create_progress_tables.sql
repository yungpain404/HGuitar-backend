-- V7: Progress schema — lesson progress tracking

CREATE TABLE progress.user_lesson_progress (
    id           UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_user_lesson_progress PRIMARY KEY,
    user_id      UNIQUEIDENTIFIER NOT NULL,
    lesson_id    UNIQUEIDENTIFIER NOT NULL,
    status       NVARCHAR(20)     NOT NULL DEFAULT 'NOT_STARTED'
                     CONSTRAINT ck_lesson_progress_status CHECK (status IN ('NOT_STARTED','IN_PROGRESS','COMPLETED')),
    completed_at DATETIME2(3),
    created_at   DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at   DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_lesson_progress_user_lesson UNIQUE (user_id, lesson_id),
    CONSTRAINT fk_lesson_progress_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_lesson_progress_lesson FOREIGN KEY (lesson_id) REFERENCES learning.lessons(id)
);

-- Indexes
CREATE INDEX ix_lesson_progress_user_id ON progress.user_lesson_progress(user_id);
CREATE INDEX ix_lesson_progress_lesson_id ON progress.user_lesson_progress(lesson_id);
