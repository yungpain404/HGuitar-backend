-- V10: Align user_roles table with JPA entity
-- The original user_roles table stores role_id (FK to roles table).
-- The UserEntity @ElementCollection maps to a role_name column.
-- We replace the table to store role_name directly for simplicity.

-- Drop old table and recreate with role_name
DROP TABLE auth.user_roles;

CREATE TABLE auth.user_roles (
    user_id   UNIQUEIDENTIFIER NOT NULL,
    role_name NVARCHAR(50)     NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_name),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE,
    CONSTRAINT ck_user_roles_role_name CHECK (role_name IN ('STUDENT','CONTENT_EDITOR','ADMIN'))
);

CREATE INDEX ix_user_roles_user_id ON auth.user_roles(user_id);
