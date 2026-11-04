-- V2: Authentication tables

CREATE TABLE auth.users (
    id               UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_users PRIMARY KEY,
    email            NVARCHAR(255)    NOT NULL,
    password_hash    VARCHAR(255)     NOT NULL,
    is_active        BIT              NOT NULL DEFAULT 1,
    created_at       DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at       DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE auth.roles (
    id   UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_roles PRIMARY KEY,
    name NVARCHAR(50)     NOT NULL,
    CONSTRAINT uq_roles_name UNIQUE (name)
);

CREATE TABLE auth.user_roles (
    user_id UNIQUEIDENTIFIER NOT NULL,
    role_id UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES auth.roles(id) ON DELETE CASCADE
);

CREATE TABLE auth.user_profiles (
    id           UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_user_profiles PRIMARY KEY,
    user_id      UNIQUEIDENTIFIER NOT NULL,
    display_name NVARCHAR(255),
    avatar_url   NVARCHAR(500),
    created_at   DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at   DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_user_profiles_user UNIQUE (user_id),
    CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE
);

CREATE TABLE auth.refresh_tokens (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_refresh_tokens PRIMARY KEY,
    user_id     UNIQUEIDENTIFIER NOT NULL,
    token_hash  VARCHAR(255)     NOT NULL,
    expires_at  DATETIME2(3)     NOT NULL,
    revoked     BIT              NOT NULL DEFAULT 0,
    created_at  DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_refresh_tokens_hash UNIQUE (token_hash),
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE
);

-- Seed default roles
INSERT INTO auth.roles (id, name) VALUES
    (NEWID(), 'STUDENT'),
    (NEWID(), 'CONTENT_EDITOR'),
    (NEWID(), 'ADMIN');

-- Indexes
CREATE INDEX ix_users_email ON auth.users(email);
CREATE INDEX ix_refresh_tokens_user_id ON auth.refresh_tokens(user_id);
CREATE INDEX ix_refresh_tokens_expires_at ON auth.refresh_tokens(expires_at);
