-- V8: User preferences — guitar type selection and onboarding flag

CREATE TABLE auth.user_preferences (
    id                   UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_user_preferences PRIMARY KEY,
    user_id              UNIQUEIDENTIFIER NOT NULL,
    guitar_type          NVARCHAR(20)
                             CONSTRAINT ck_user_preferences_guitar_type CHECK (guitar_type IN ('ACOUSTIC','CLASSICAL')),
    onboarding_completed BIT              NOT NULL DEFAULT 0,
    created_at           DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at           DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_user_preferences_user UNIQUE (user_id),
    CONSTRAINT fk_user_preferences_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE
);

CREATE INDEX ix_user_preferences_user_id ON auth.user_preferences(user_id);
