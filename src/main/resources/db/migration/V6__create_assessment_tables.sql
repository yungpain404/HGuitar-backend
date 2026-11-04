-- V6: Assessment schema — quizzes, questions, answers, attempts

CREATE TABLE assessment.quizzes (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_quizzes PRIMARY KEY,
    lesson_id   UNIQUEIDENTIFIER NOT NULL,
    title_en    NVARCHAR(500)    NOT NULL,
    title_vi    NVARCHAR(500),
    created_at  DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_quizzes_lesson UNIQUE (lesson_id),
    CONSTRAINT fk_quizzes_lesson FOREIGN KEY (lesson_id) REFERENCES learning.lessons(id) ON DELETE CASCADE
);

CREATE TABLE assessment.questions (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_questions PRIMARY KEY,
    quiz_id     UNIQUEIDENTIFIER NOT NULL,
    sort_order  INT              NOT NULL,
    text_en     NVARCHAR(2000)   NOT NULL,
    text_vi     NVARCHAR(2000),
    CONSTRAINT uq_questions_quiz_sort UNIQUE (quiz_id, sort_order),
    CONSTRAINT ck_questions_sort_order CHECK (sort_order >= 1),
    CONSTRAINT fk_questions_quiz FOREIGN KEY (quiz_id) REFERENCES assessment.quizzes(id) ON DELETE CASCADE
);

CREATE TABLE assessment.answers (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_answers PRIMARY KEY,
    question_id UNIQUEIDENTIFIER NOT NULL,
    text_en     NVARCHAR(1000)   NOT NULL,
    text_vi     NVARCHAR(1000),
    is_correct  BIT              NOT NULL DEFAULT 0,
    CONSTRAINT fk_answers_question FOREIGN KEY (question_id) REFERENCES assessment.questions(id) ON DELETE CASCADE
);

CREATE TABLE assessment.quiz_attempts (
    id             UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_quiz_attempts PRIMARY KEY,
    quiz_id        UNIQUEIDENTIFIER NOT NULL,
    user_id        UNIQUEIDENTIFIER NOT NULL,
    attempt_number INT              NOT NULL,
    started_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    submitted_at   DATETIME2(3),
    score          DECIMAL(5,2),
    status         NVARCHAR(20)     NOT NULL DEFAULT 'IN_PROGRESS'
                       CONSTRAINT ck_quiz_attempts_status CHECK (status IN ('IN_PROGRESS','SUBMITTED')),
    created_at     DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_quiz_attempts_quiz FOREIGN KEY (quiz_id) REFERENCES assessment.quizzes(id),
    CONSTRAINT fk_quiz_attempts_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE
);

CREATE TABLE assessment.quiz_attempt_answers (
    id                 UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_quiz_attempt_answers PRIMARY KEY,
    attempt_id         UNIQUEIDENTIFIER NOT NULL,
    question_id        UNIQUEIDENTIFIER NOT NULL,
    selected_answer_id UNIQUEIDENTIFIER NOT NULL,
    is_correct         BIT              NOT NULL DEFAULT 0,
    CONSTRAINT uq_quiz_attempt_answers_attempt_question UNIQUE (attempt_id, question_id),
    CONSTRAINT fk_quiz_attempt_answers_attempt FOREIGN KEY (attempt_id) REFERENCES assessment.quiz_attempts(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX ix_quiz_attempts_quiz_user ON assessment.quiz_attempts(quiz_id, user_id);
CREATE INDEX ix_quiz_attempt_answers_attempt_id ON assessment.quiz_attempt_answers(attempt_id);
